package pe.edu.utp.sistemaimprenta.util;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public final class Notification {

    private Notification() {
    }

    public static void showNotification(String title, String text, int seconds, NotificationType notificationType) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> showNotification(title, text, seconds, notificationType));
            return;
        }
        showNotificationInternal(title, text, seconds, notificationType);
    }

    private static void showNotificationInternal(String title, String text, int seconds,
                                                 NotificationType notificationType) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(seconds))
                .darkStyle()
                .position(Pos.BOTTOM_RIGHT);

        ImageView graphic = switch (notificationType) {
            case CONFIRM -> loadImage("confirm.png");
            case ERROR -> loadImage("error.png");
            case INFORMATION -> loadImage("info.png");
            case WARNING -> loadImage("warning.png");
            case SUCCESS -> loadImage("success.png");
            case SIMPLE -> null;
        };

        if (graphic != null) {
            notification.graphic(graphic);
        }

        notification.show();
    }

    private static ImageView loadImage(String fileName) {
        try {
            Image image = new Image(Notification.class.getResourceAsStream("/imagen/" + fileName));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);
            return imageView;
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen: " + fileName);
            return null;
        }
    }
}

