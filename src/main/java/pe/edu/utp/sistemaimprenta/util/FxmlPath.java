package pe.edu.utp.sistemaimprenta.util;

public enum FxmlPath {
    AUTH("/views/AuthView.fxml"),
    LOGIN_PANE("/views/LoginPane.fxml"),
    REGISTER_PANE("/views/RegisterPane.fxml"),
    DASHBOARD("/views/DashboardView.fxml"),
    SIDEBAR_ITEM("/views/SidebarItem.fxml"),
    CUSTOMER_PANE("/views/CustomersPane.fxml"),
    USER_PANE("/views/UsersPane.fxml");

    private final String path;

    private FxmlPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
