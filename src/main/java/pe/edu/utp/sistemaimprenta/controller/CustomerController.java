package pe.edu.utp.sistemaimprenta.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pe.edu.utp.sistemaimprenta.dao.CustomerDao;
import pe.edu.utp.sistemaimprenta.model.Customer;
import pe.edu.utp.sistemaimprenta.util.Notification;
import pe.edu.utp.sistemaimprenta.util.NotificationType;

public class CustomerController implements Initializable {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TableColumn<Customer, String> colApellidos;

    @FXML
    private TableColumn<Customer, String> colDireccion;

    @FXML
    private TableColumn<Customer, String> colDni;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, Integer> colId;

    @FXML
    private TableColumn<Customer, String> colNombres;

    @FXML
    private TableColumn<Customer, String> colTelefono;

    @FXML
    private TableView<Customer> tablaDatos;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtTelefono;

    private ObservableList<Customer> listaClientes;
    private CustomerDao customerDao;

    public CustomerController() {
        customerDao = new CustomerDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setOnAction(e->registrar());
        btnActualizar.setOnAction(e->actualizar());
        btnEliminar.setOnAction(e->eliminar());
        btnLimpiar.setOnAction(e->limpiarCampos());

        listaClientes = FXCollections.observableArrayList(customerDao.findAll());
        tablaDatos.setItems(listaClientes);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNombres.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        colApellidos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephoneNumber()));
        colDni.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDni()));
        colDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));

        // Evento para llenar campos al seleccionar fila
        tablaDatos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
        if (newSel != null) {
            txtDni.setText(newSel.getDni());
            txtApellidos.setText(newSel.getLastName());
            txtNombres.setText(newSel.getName());
            txtTelefono.setText(newSel.getTelephoneNumber());
            txtEmail.setText(newSel.getEmail());
            txtDireccion.setText(newSel.getAddress());
        }
    });


    }

    private void registrar() {

        Customer c = new Customer();
        c.setDni(txtDni.getText());
        c.setName(txtNombres.getText());
        c.setLastName(txtNombres.getText());
        c.setEmail(txtEmail.getText());
        c.setTelephoneNumber(txtNombres.getText());
        c.setAddress(txtDireccion.getText());
        customerDao.save(c);
        refrescarTabla();
        limpiarCampos();

    }

    private void actualizar() {
        Customer seleccionado = tablaDatos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {

            seleccionado.setName(txtNombres.getText());
            seleccionado.setEmail(txtEmail.getText());
            seleccionado.setLastName(txtApellidos.getText());
            seleccionado.setDni(txtDni.getText());
            seleccionado.setTelephoneNumber(txtTelefono.getText());
            seleccionado.setAddress(txtDireccion.getText());
            
            customerDao.uptade(seleccionado);
            Notification.showNotification("REGISTRO CLIENTE", "Con exito", 4, NotificationType.SUCCESS);
            refrescarTabla();
            limpiarCampos();

        }
    }

    private void eliminar() {
        Customer seleccionado = tablaDatos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            customerDao.delete(seleccionado.getId());
            refrescarTabla();
            limpiarCampos();
           
        }
    }

    private void refrescarTabla() {
        listaClientes.setAll(customerDao.findAll());
        
    }

    private void limpiarCampos() {
        txtDni.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
    }
}
