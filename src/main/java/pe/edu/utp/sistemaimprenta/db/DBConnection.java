package pe.edu.utp.sistemaimprenta.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import pe.edu.utp.sistemaimprenta.util.Logger;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            String url = DBConfig.get("db.url");
            String user = DBConfig.get("db.username");
            String password = DBConfig.get("db.password");

            this.connection = DriverManager.getConnection(url, user, password);
            Logger.info("Se ha establecido conexión con la base de datos");
        } catch (SQLException e) {
            Logger.error("No se pudo establecer conexion con la base de datos", e);
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            Logger.error("No se pudo cerrar la conexión a la base de datos", e);
        }
    }
}
