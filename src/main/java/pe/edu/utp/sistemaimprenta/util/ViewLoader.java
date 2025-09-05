package pe.edu.utp.sistemaimprenta.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class ViewLoader {

    private ViewLoader() {
    }
    
    private static FXMLLoader createFXMLLoader(String fxmlPath) throws IOException {
        URL resource = ViewLoader.class.getResource(fxmlPath);
        if (resource == null) {
            throw new IOException("No se encontró el archivo FXML: " + fxmlPath);
        }
        return new FXMLLoader(resource);
    }

    public static void openWindow(String fxmlPath, String title, boolean resizable) {
        try {
            Parent root = createFXMLLoader(fxmlPath).load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al abrir ventana: " + fxmlPath + " " + e);
        }
    }

    public static void changeMainPanel(Pane mainPanel, String fxmlPath) {
        try {
            FXMLLoader loader = createFXMLLoader(fxmlPath);
            Parent root = loader.load();
            mainPanel.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Error al cambiar el panel: " + fxmlPath + " " + e);
        }
    }

    public static <T> T openWindowGetController(String fxmlPath, String title, boolean resizable) {
        try {
            FXMLLoader loader = createFXMLLoader(fxmlPath);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error al abrir ventana con controlador: " + fxmlPath + " " + e);
            return null;
        }
    }
 
}
