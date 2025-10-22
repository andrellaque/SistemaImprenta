
package pe.edu.utp.sistemaimprenta.util;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

public class Export {

    public static <T> void exportToExcel(Window window, List<T> data, String fileName,
                                         String[] headers, Function<T, Object[]> mapper) {
        if (data == null || data.isEmpty()) return;

        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar archivo Excel");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
        fc.setInitialFileName(fileName + ".xlsx");
        File file = fc.showSaveDialog(window);
        if (file == null) return;

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(fileName);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (T item : data) {
                Object[] values = mapper.apply(item);
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < values.length; i++) {
                    row.createCell(i).setCellValue(values[i] != null ? values[i].toString() : "");
                }
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            Message.info("Archivo Excel guardado con éxito:\n" + file.getAbsolutePath());
        } catch (Exception e) {
            Message.error("Error al exportar a Excel: " + e.getMessage());
        }
    }

    public static <T> void exportToCsv(Window window, List<T> data, String fileName,
                                       String[] headers, Function<T, Object[]> mapper) {
        if (data == null || data.isEmpty()) return;

        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar archivo CSV");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
        fc.setInitialFileName(fileName + ".csv");
        File file = fc.showSaveDialog(window);
        if (file == null) return;

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write(String.join(",", headers));
            writer.newLine();

            for (T item : data) {
                Object[] values = mapper.apply(item);
                for (int i = 0; i < values.length; i++) {
                    String val = values[i] != null ? values[i].toString() : "";
                    if (val.contains(",") || val.contains("\""))
                        val = "\"" + val.replace("\"", "\"\"") + "\"";
                    writer.write(val);
                    if (i < values.length - 1) writer.write(",");
                }
                writer.newLine();
            }

            Message.info("Archivo CSV guardado con éxito:\n" + file.getAbsolutePath());
        } catch (Exception e) {
            Message.error("Error al exportar a CSV: " + e.getMessage());
        }
    }
}

