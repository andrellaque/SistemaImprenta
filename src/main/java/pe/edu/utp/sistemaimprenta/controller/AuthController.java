package pe.edu.utp.sistemaimprenta.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import pe.edu.utp.sistemaimprenta.util.FxmlPath;
import pe.edu.utp.sistemaimprenta.util.ViewLoader;

public class AuthController implements Initializable{

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private AnchorPane mainPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLoginPanel();
        btnLogin.setOnAction(e -> showLoginPanel());
        btnRegister.setOnAction(e -> showRegisterPanel());
    }
    @FXML
    private void showLoginPanel() {
        changePanel(FxmlPath.LOGIN_PANE.getPath(), btnLogin);
    }

    @FXML
    private void showRegisterPanel() {
        changePanel(FxmlPath.REGISTER_PANE.getPath(), btnRegister);
    }

    private void changePanel(String path, Button selectedButton) {
        ViewLoader.changeMainPanel(mainPanel, path);

        resetStyleButton(btnLogin);
        resetStyleButton(btnRegister);

        selectedButton.getStyleClass().add("menu-button-selected");

    }

    private void resetStyleButton(Button b) {
        b.getStyleClass().removeAll("menu-button-selected");
    }
   

}
