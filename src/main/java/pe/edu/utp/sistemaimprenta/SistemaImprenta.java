package pe.edu.utp.sistemaimprenta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import pe.edu.utp.sistemaimprenta.model.Customer;
import pe.edu.utp.sistemaimprenta.model.Order;
import pe.edu.utp.sistemaimprenta.model.Product;
import pe.edu.utp.sistemaimprenta.model.ProductType;
import pe.edu.utp.sistemaimprenta.model.ProductionState;
import pe.edu.utp.sistemaimprenta.util.FxmlPath;
import pe.edu.utp.sistemaimprenta.util.ViewLoader;


public class SistemaImprenta extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewLoader.openWindow(FxmlPath.AUTH.getPath(), "Login", false);
    }

    public static void main(String[] args) {
        
        Customer cliente= new Customer(1,"123456798","Javier Boulanger","951777260","javier@gmail.com","jlo");

        List<Product> products =Arrays.asList(new Product(1,ProductType.GIGANTOGRAFIA,"esto es ua gigantografia",29),
                new Product(2,ProductType.AFICHE,"esto es afiche",35));

        Order or= new Order(1,cliente, products, LocalDateTime.now(),LocalDateTime.now(), ProductionState.EN_DISENO);

        System.out.println(or);

        //launch(args);
    }
}

