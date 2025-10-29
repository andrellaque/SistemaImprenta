package pe.edu.utp.sistemaimprenta;

import javafx.application.Application;
import javafx.stage.Stage;
import pe.edu.utp.sistemaimprenta.util.EncryptPassword;
import pe.edu.utp.sistemaimprenta.util.FxmlPath;
import pe.edu.utp.sistemaimprenta.util.ViewLoader;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewLoader.openWindow(FxmlPath.AUTH.getPath(), "Login", false);
    }

    public static void main(String[] args) {
        
        launch(args);
        //System.out.println(EncryptPassword.encrypt("12"));;  
    }
}

