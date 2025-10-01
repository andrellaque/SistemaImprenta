package pe.edu.utp.sistemaimprenta.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SidebarItemController {

    @FXML
    private ImageView icon;

    @FXML
    private Label label;

    public void setLabelText(String text) {
        label.setText(text);
    }

    public void setIconImage(Image image) {
        icon.setImage(image);
    }

}
