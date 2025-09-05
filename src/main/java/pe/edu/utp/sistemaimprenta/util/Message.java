package pe.edu.utp.sistemaimprenta.util;

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
}
