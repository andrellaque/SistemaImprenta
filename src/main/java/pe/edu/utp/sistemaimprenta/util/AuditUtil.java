package pe.edu.utp.sistemaimprenta.util;

import java.time.LocalDateTime;
import pe.edu.utp.sistemaimprenta.dao.AuditDao;
import pe.edu.utp.sistemaimprenta.model.*;

public class AuditUtil {

    private static final AuditDao dao = new AuditDao();

    public static void registrar(User usuario, String descripcion, AuditType tipo) { 
        Audit item = new Audit(usuario,descripcion,LocalDateTime.now(),tipo);
        dao.registrar(item);
    }
}

