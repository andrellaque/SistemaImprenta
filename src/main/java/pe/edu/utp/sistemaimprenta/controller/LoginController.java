package pe.edu.utp.sistemaimprenta.controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pe.edu.utp.sistemaimprenta.util.Message;
import pe.edu.utp.sistemaimprenta.util.Validator;

public class LoginController implements Initializable {

    @FXML
    private Button btnEnter;

    @FXML
    private Label lblError;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEnter.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String u = txtUsername.getText();
        String p = txtPassword.getText();

        String error = Validator.validateInputFieldsLogin(u, p);
        if (error != null) {
            Message.showMessage(lblError, error, "red");
            return;
        }

        boolean inicioExitoso = true;

        if (inicioExitoso) {
            Message.showMessage(lblError, "¡Inicio de sesiòn exitoso!", "green");
            closeCurrentStage();
        } else {
            Message.showMessage(lblError, "Credenciales incorrectas", "red");
        }
    }

    private void closeCurrentStage() {
        Stage currentStage = (Stage) txtUsername.getScene().getWindow();
        currentStage.close();
    }

}
