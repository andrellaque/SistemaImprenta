
package pe.edu.utp.sistemaimprenta.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {

    private static final String CONFIG_FILE = "db.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream is = DBConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(is);
        } catch (IOException e) {
            System.err.println("Error al cargar la configuración de la base de datos " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}