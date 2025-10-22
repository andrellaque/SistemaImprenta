package pe.edu.utp.sistemaimprenta.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class Message {
    private Message(){}
    
    public static void showMessage(Label label, String message, String style) {
        label.getStyleClass().removeAll("label-error", "label-success", "label-neutral");

        switch (style.toLowerCase()) {
            case "red" -> label.getStyleClass().add("label-error");
            case "green" -> label.getStyleClass().add("label-success");
            default -> label.getStyleClass().add("label-neutral");
        }

        label.setText(message);
    }
    
    public static void info(String msg) {
        show(msg, Alert.AlertType.INFORMATION);
    }
    public static void error(String msg) {
        show(msg, Alert.AlertType.ERROR);
    }
    private static void show(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Información");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
