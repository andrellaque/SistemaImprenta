package pe.edu.utp.sistemaimprenta.controller;

import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pe.edu.utp.sistemaimprenta.model.Audit;

public class AuditItemController {

    @FXML
    private HBox root;
    @FXML
    private StackPane iconContainer;
    @FXML
    private ImageView iconView;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblTipo;

    public void setData(Audit item) {
        lblUsuario.setText(item.user().getUsername());
        lblDescripcion.setText(item.description());
        lblFecha.setText(item.date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblTipo.setText(item.type().name().toLowerCase());

        String estilo = item.type().name().toLowerCase();
        iconContainer.getStyleClass().add(estilo);
        lblTipo.getStyleClass().add(estilo + "-label");

        String icono = switch (item.type()) {
            case CREACION ->
                "add.png";
            case MODIFICACION ->
                "edit.png";
            case ACTUALIZACION ->
                "refresh.png";
            case ELIMINACION ->
                "delete.png";
            case CONSULTA ->
                "consult.png";
            case LOGIN ->
                "login.png";
            case LOGOUT ->
                "logout.png";
        };

        iconView.setImage(new Image(getClass().getResourceAsStream("/images/icons/" + icono)));
    }

    public HBox getRoot() {
        return root;
    }

}
