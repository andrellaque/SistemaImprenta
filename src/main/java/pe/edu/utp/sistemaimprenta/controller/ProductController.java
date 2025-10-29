package pe.edu.utp.sistemaimprenta.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.sistemaimprenta.dao.ProductDao;
import pe.edu.utp.sistemaimprenta.model.Product;
import pe.edu.utp.sistemaimprenta.model.ProductType;
import pe.edu.utp.sistemaimprenta.model.User;
import pe.edu.utp.sistemaimprenta.util.Export;
import pe.edu.utp.sistemaimprenta.util.Message;
import pe.edu.utp.sistemaimprenta.util.Notification;
import pe.edu.utp.sistemaimprenta.util.NotificationType;
import pe.edu.utp.sistemaimprenta.util.UserAware;

public class ProductController implements Initializable, UserAware {

    @FXML
    private TableView<Product> tablaProductos;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colNombre;
    @FXML
    private TableColumn<Product, String> colDescripcion;
    @FXML
    private TableColumn<Product, String> colTipo;
    @FXML
    private TableColumn<Product, Double> colPrecio;
    @FXML
    private TableColumn<Product, String> colEstado;

    @FXML
    private Button btnActualizarModal;

    @FXML
    private Label lblTituloModal;

    @FXML
    private Button btnNuevo, btnGuardar, btnCancelar, btnActualizar, btnEliminar;
    @FXML
    private ComboBox<String> cmbFiltro;
    @FXML
    private ComboBox<ProductType> cmbTipo;
    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private TextField txtBuscar, txtNombre, txtPrecioBase;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblError, lblError1;
    @FXML
    private Pane paneNuevoProducto;
    @FXML
    private ImageView btnBuscar;

    @FXML
    private Button btnCsv, btnExcel;

    private ObservableList<Product> listaProductos;
    private ProductDao productoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productoDao = new ProductDao();
        listaProductos = FXCollections.observableArrayList(productoDao.findAll());
        tablaProductos.setItems(listaProductos);

        configurarColumnas();
        configurarCombos();
        configurarEventos();
        btnExcel.setOnAction(this::exportarExcel);
        btnCsv.setOnAction(this::exportarCsv);
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType().name()));
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getBasePrice()).asObject());
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getState()));
    }

    private void configurarCombos() {
        cmbTipo.setItems(FXCollections.observableArrayList(ProductType.values()));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbFiltro.setItems(FXCollections.observableArrayList("Nombre", "Tipo", "Estado"));
    }

    private void configurarEventos() {
        btnNuevo.setOnAction(e -> {
            lblTituloModal.setText("Nuevo Producto");
            limpiarCampos();
            paneNuevoProducto.setVisible(true);
            btnGuardar.setVisible(true);
            btnActualizarModal.setVisible(false);
        });
        
        btnActualizar.setOnAction(e -> {
            lblTituloModal.setText("Editar Producto");
            Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                Message.showMessage(lblError, "Seleccione un producto de la tabla para actualizar", "red");
                return;
            }
            mostrarSeleccionado(seleccionado);
            paneNuevoProducto.setVisible(true);
            btnGuardar.setVisible(false);
            btnActualizarModal.setVisible(true);
        });
        
        btnCancelar.setOnAction(e -> paneNuevoProducto.setVisible(false));
        btnGuardar.setOnAction(e -> registrarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());
        btnActualizarModal.setOnAction(e -> actualizarProducto());
        
        tablaProductos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> mostrarSeleccionado(newSel));
    }

    private void registrarProducto() {
        String error = validarCampos();
        if (error != null) {
            Message.showMessage(lblError1, error, "red");
            Notification.showNotification("VALIDACIÓN", "", 4, NotificationType.ERROR);
            return;
        }

        Product p = new Product();
        llenarDatos(p);

        if (productoDao.save(p, usuarioActual)) {
            Notification.showNotification("PRODUCTO", "Registrado correctamente", 4, NotificationType.SUCCESS);
            refrescarTabla();
            limpiarCampos();
            paneNuevoProducto.setVisible(false); 
        }
    }

    @FXML
    private void actualizarProducto() {
        Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Message.showMessage(lblError, "Seleccione un producto de la tabla", "red");
            return;
        }

        String error = validarCampos();
        if (error != null) {
            Message.showMessage(lblError1, error, "red");
            return;
        }

        llenarDatos(seleccionado);

        if (productoDao.update(seleccionado, usuarioActual)) {
            Notification.showNotification("PRODUCTO", "Actualizado correctamente", 4, NotificationType.SUCCESS);
            refrescarTabla();
            limpiarCampos();
            paneNuevoProducto.setVisible(false);
        }
    }

    private void eliminarProducto() {
        Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Message.showMessage(lblError, "Debe seleccionar un producto para eliminar", "red");
            return;
        }

        if (productoDao.delete(seleccionado.getId(), usuarioActual)) {
            Notification.showNotification("PRODUCTO", "Eliminado correctamente", 4, NotificationType.SUCCESS);
            refrescarTabla();
        }
    }

    private void refrescarTabla() {
        listaProductos.setAll(productoDao.findAll());
    }

    private void llenarDatos(Product p) {
        p.setName(txtNombre.getText().trim());
        p.setDescription(txtDescripcion.getText().trim());
        p.setType(cmbTipo.getValue());
        p.setBasePrice(Double.valueOf(txtPrecioBase.getText()));
        p.setState(cmbEstado.getValue());
    }

    private void mostrarSeleccionado(Product p) {
        if (p == null) {
            return;
        }
        txtNombre.setText(p.getName());
        txtDescripcion.setText(p.getDescription());
        cmbTipo.setValue(p.getType());
        txtPrecioBase.setText(String.valueOf(p.getBasePrice()));
        cmbEstado.setValue(p.getState());
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecioBase.clear();
        cmbTipo.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
    }

    private String validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            return "La descripción es obligatoria";
        }
        if (cmbTipo.getValue() == null) {
            return "Debe seleccionar un tipo";
        }
        //if (!Validator.isValidPrice(txtPrecioBase.getText())) return "El precio base no es válido";
        if (cmbEstado.getValue() == null) {
            return "Debe seleccionar un estado";
        }
        return null;
    }

    private User usuarioActual;

    @Override
    public void setUsuarioActual(User usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    private void exportarExcel(ActionEvent e) {
        String[] headers = {"ID", "Nombre", "Descripción", "Tipo", "Precio", "Estado"};
        Export.exportToExcel(
                tablaProductos.getScene().getWindow(),
                tablaProductos.getItems(),
                "Productos",
                headers,
                p -> new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getType(),
                    p.getBasePrice(),
                    p.getState()
                }
        );
    }

    private void exportarCsv(ActionEvent e) {
        System.out.println("adsad");
        String[] headers = {"ID", "Nombre", "Descripción", "Tipo", "Precio", "Estado"};
        Export.exportToCsv(
                tablaProductos.getScene().getWindow(),
                tablaProductos.getItems(),
                "Productos",
                headers,
                p -> new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getType(),
                    p.getBasePrice(),
                    p.getState()
                }
        );
    }
}
