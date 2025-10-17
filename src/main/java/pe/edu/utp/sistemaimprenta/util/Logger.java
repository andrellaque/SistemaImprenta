package pe.edu.utp.sistemaimprenta.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "log.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private Logger() {}

    private static void log(String level, String message) {
        try (PrintWriter wr = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String line = String.format("[%s] [%s] %s", LocalDateTime.now().format(FORMATTER), level, message);
            wr.println(line); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String s) {
        log("INFO", s);
    }

    public static void warning(String s) {
        log("WARNING", s);
    }

    public static void error(String s, Exception e) {
        log("ERROR", s + " | "+e.getClass().getName()+": " + e.getMessage());
    }
}
