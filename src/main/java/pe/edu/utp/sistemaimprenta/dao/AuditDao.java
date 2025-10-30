package pe.edu.utp.sistemaimprenta.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.sistemaimprenta.db.DBConnection;
import pe.edu.utp.sistemaimprenta.model.Audit;
import pe.edu.utp.sistemaimprenta.model.AuditType;
import pe.edu.utp.sistemaimprenta.model.User;

public class AuditDao {

    private static final Logger log = LoggerFactory.getLogger(AuditDao.class);

    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }

    public void registrar(Audit item) {
        String sql = "INSERT INTO Auditoria (id_usuario, descripcion, fecha, tipo) VALUES (?, ?, ?, ?)";

        try (
             PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, item.user().getId());
            ps.setString(2, item.description());
            ps.setTimestamp(3, Timestamp.valueOf(item.date()));
            ps.setString(4, item.type().name());
            ps.executeUpdate();
            log.info("Auditoría registrada ["+ item.description()+"]");

        } catch (SQLException e) {
            log.error("Error al registrar auditoría", e);
        }
    }

    public List<Audit> listar() {
        List<Audit> lista = new ArrayList<>();
        String sql = """
            SELECT a.id_auditoria, a.descripcion, a.fecha, a.tipo, 
                   u.id_usuario, u.nombre AS nombre_usuario
            FROM Auditoria a
            LEFT JOIN Usuario u ON a.id_usuario = u.id_usuario
            ORDER BY a.fecha DESC
        """;

        try (
            PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_usuario"));
                user.setUsername(rs.getString("nombre_usuario"));

                Audit item = new Audit(
                        user,
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        AuditType.valueOf(rs.getString("tipo"))
                );

                lista.add(item);
            }

        } catch (SQLException e) {
            log.error("Error al listar auditorías", e);
        }

        return lista;
    }
}
