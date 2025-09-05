package pe.edu.utp.sistemaimprenta.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pe.edu.utp.sistemaimprenta.util.Message;
import pe.edu.utp.sistemaimprenta.util.Validator;


public class RegisterController implements Initializable {
    
    @FXML
    private Button btnEnter;
    
    @FXML
    private Label lblError;
    
    @FXML
    private PasswordField txtConfirmPassword;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private TextField txtUsername;
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEnter.setOnAction(e -> handleRegister());
    }
    
    
    private void handleRegister() {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        
        String error = Validator.validateInputFieldsRegister(username, email, password, confirmPassword);
        if (error != null) {
            Message.showMessage(lblError, error, "red");
            return;
        }
        
        boolean registroExitoso=true;
        
        if (registroExitoso) {
             Message.showMessage(lblError, "¡Registro exitoso!", "green");
            clear();
        } else {
            Message.showMessage(lblError, "Error al registrar usuario", "red");
        } 

    }
    
    private void clear() {
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
    }
    
}
