package pe.edu.utp.sistemaimprenta.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.sistemaimprenta.dao.*;
import pe.edu.utp.sistemaimprenta.model.*;
import pe.edu.utp.sistemaimprenta.util.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable, UserAware {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnAgregarDetalle;

    @FXML
    private ImageView btnBuscar;

    @FXML
    private Button btnCancelarPedido;

    @FXML
    private Button btnCsv;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnExcel;

    @FXML
    private Button btnGuardarPedido;

    @FXML
    private Button btnQuitarDetalle;

    @FXML
    private ComboBox<Customer> cmbCliente;

    @FXML
    private ComboBox<OrderState> cmbEstado;

    @FXML
    private ComboBox<String> cmbFiltro;

    @FXML
    private ComboBox<Product> cmbProducto;

    @FXML
    private Label lblErrorDetalle;

    @FXML
    private Label lblErrorPedido;

    @FXML
    private Pane paneNuevoPedido;

    @FXML
    private TableView<Order> tablaDatos;

    @FXML
    private TableView<OrderDetail> tablaDetalle;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private DatePicker dateEntrega;
    @FXML
    private Label lblTotal;

    @FXML
    private TableColumn<Order, Integer> colId;
    @FXML
    private TableColumn<Order, String> colVendedor;
    @FXML
    private TableColumn<Order, String> colCliente;
    @FXML
    private TableColumn<Order, String> colEstado;
    @FXML
    private TableColumn<Order, String> colFechaEntrega;
    @FXML
    private TableColumn<Order, String> colFechaRegistro;
    @FXML
    private TableColumn<Order, Double> colTotal;
    @FXML
    private TableColumn<OrderDetail, String> colProductoDetalle;
    @FXML
    private TableColumn<OrderDetail, Integer> colCantidadDetalle;
    @FXML
    private TableColumn<OrderDetail, Double> colPrecioDetalle;
    @FXML
    private TableColumn<OrderDetail, Double> colSubtotalDetalle;

    private ObservableList<Order> listaPedidos;
    private final ObservableList<OrderDetail> detalles = FXCollections.observableArrayList();

    private final OrderDao orderDao = new OrderDao();
    private final CustomerDao customerDao = new CustomerDao();
    private final ProductDao productDao = new ProductDao();

    private User usuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        configurarDetalle();
        configurarCombos();
        configurarBotones();
        refrescarTabla();
        paneNuevoPedido.setVisible(false);
    }

    @Override
    public void setUsuarioActual(User user) {
        this.usuarioActual = user;
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colVendedor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUser().getUsername()));
        colCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomer().getName()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getState().name()));
        colFechaEntrega.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getDeliveryDate() != null ? data.getValue().getDeliveryDate().toLocalDate().toString() : ""));
        colFechaRegistro.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCreatedAt() != null ? data.getValue().getCreatedAt().toLocalDate().toString() : ""));
        colTotal.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getTotalAmount()).asObject());
    }

    private void configurarDetalle() {
        colProductoDetalle.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getProduct().getName()));
        colCantidadDetalle.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getQuantity()).asObject());
        colPrecioDetalle.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getUnitPrice()).asObject());
        colSubtotalDetalle.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getSubtotal()).asObject());
        tablaDetalle.setItems(detalles);
    }

    private void configurarCombos() {
        cmbFiltro.setItems(FXCollections.observableArrayList("Cliente", "Vendedor", "Estado"));
        cmbFiltro.getSelectionModel().selectFirst();

        cmbCliente.setItems(FXCollections.observableArrayList(customerDao.findAll()));
        cmbProducto.setItems(FXCollections.observableArrayList(productDao.findAll()));
        cmbEstado.setItems(FXCollections.observableArrayList(OrderState.values()));
    }

    private void configurarBotones() {
        btnBuscar.setOnMouseClicked(e -> buscarPedido());
        btnActualizar.setOnAction(e -> refrescarTabla());
        btnAgregar.setOnAction(e -> paneNuevoPedido.setVisible(true));
        btnEliminar.setOnAction(e -> eliminarPedido());
        btnGuardarPedido.setOnAction(e -> registrarPedido());
        btnCancelarPedido.setOnAction(e -> paneNuevoPedido.setVisible(false));
        btnAgregarDetalle.setOnAction(e -> agregarDetalle());
        btnQuitarDetalle.setOnAction(e -> quitarDetalle());
    }


    private void agregarDetalle() {
        Product p = cmbProducto.getValue();
        if (p == null) {
            Message.showMessage(lblErrorDetalle, "Seleccione un producto", "red");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Message.showMessage(lblErrorDetalle, "Cantidad inválida", "red");
            return;
        }

        double precio = p.getBasePrice();

        OrderDetail detalle = new OrderDetail(p, cantidad, precio);
        detalles.add(detalle);
        tablaDetalle.refresh();
        actualizarTotal();
    }

    private void quitarDetalle() {
        OrderDetail seleccionado = tablaDetalle.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            detalles.remove(seleccionado);
            actualizarTotal();
        } else {
            Message.showMessage(lblErrorDetalle, "Seleccione un detalle", "red");
        }
    }

    private void actualizarTotal() {
        double total = detalles.stream().mapToDouble(OrderDetail::getSubtotal).sum();
        lblTotal.setText(String.format("%.2f", total));
    }

    private void registrarPedido() {
        try {
            Customer cliente = cmbCliente.getValue();
            LocalDate fechaEntrega = dateEntrega.getValue();
            OrderState estado = cmbEstado.getValue();

            if (cliente == null || fechaEntrega == null || detalles.isEmpty() || estado == null) {
                Message.showMessage(lblErrorDetalle, "Complete todos los campos", "red");
                return;
            }

            Order pedido = new Order();
            pedido.setCustomer(cliente);
            pedido.setUser(usuarioActual);
            pedido.setState(estado);
            pedido.setDeliveryDate(fechaEntrega.atStartOfDay());
            pedido.setDetails(detalles);
            pedido.setTotalAmount(detalles.stream().mapToDouble(OrderDetail::getSubtotal).sum());

            boolean exito = orderDao.save(pedido, usuarioActual);
            if (exito) {
                Notification.showNotification("PEDIDO", "Pedido registrado con éxito", 4, NotificationType.SUCCESS);
                paneNuevoPedido.setVisible(false);
                refrescarTabla();
                detalles.clear();
                actualizarTotal();
            } else {
                Message.showMessage(lblErrorDetalle, "Error al registrar pedido", "red");
            }

        } catch (Exception e) {
            Message.showMessage(lblErrorDetalle, "Error: " + e.getMessage(), "red");
        }
    }

    private void eliminarPedido() {
        Order seleccionado = tablaDatos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Message.showMessage(lblErrorPedido, "Seleccione un pedido", "red");
            return;
        }

        boolean exito = orderDao.delete(seleccionado.getId(), usuarioActual);
        if (exito) {
            Notification.showNotification("PEDIDO", "Pedido eliminado", 4, NotificationType.SUCCESS);
            refrescarTabla();
        } else {
            Message.showMessage(lblErrorPedido, "Error al eliminar pedido", "red");
        }
    }

    private void refrescarTabla() {
        List<Order> pedidos = orderDao.findAll();
        listaPedidos = FXCollections.observableArrayList(pedidos);
        tablaDatos.setItems(listaPedidos);
    }

    private void buscarPedido() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            refrescarTabla();
            return;
        }

        String filtro = cmbFiltro.getValue();
        List<Order> pedidos = orderDao.findAll();

        listaPedidos.setAll(pedidos.stream().filter(p -> {
            switch (filtro) {
                case "Cliente":
                    return p.getCustomer().getName().toLowerCase().contains(texto);
                case "Vendedor":
                    return p.getUser().getUsername().toLowerCase().contains(texto);
                case "Estado":
                    return p.getState().name().toLowerCase().contains(texto);
                default:
                    return false;
            }
        }).toList());
    }
}
