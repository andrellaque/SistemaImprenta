
package pe.edu.utp.sistemaimprenta.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {

    private static final String CONFIG_FILE = "db.properties";
    private static Properties properties;
    private static final Logger log = LoggerFactory.getLogger(DBConfig.class);
    
    static {
        properties = new Properties();
        try (InputStream is = DBConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(is);
        } catch (IOException e) {
             log.error("No se pudo cargar configuración de la base de datos",e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}