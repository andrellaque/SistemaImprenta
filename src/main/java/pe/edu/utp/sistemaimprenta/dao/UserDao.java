package pe.edu.utp.sistemaimprenta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.sistemaimprenta.db.DBConnection;
import pe.edu.utp.sistemaimprenta.model.AuditType;
import pe.edu.utp.sistemaimprenta.model.User;
import pe.edu.utp.sistemaimprenta.model.UserType;
import pe.edu.utp.sistemaimprenta.util.AuditUtil;
import pe.edu.utp.sistemaimprenta.util.EncryptPassword;

public class UserDao implements CrudDao<User> {

    private User user;
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    
    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }
    
    public boolean existsUser(String username) {
        String query = "SELECT COUNT(*) FROM Usuario WHERE nombre = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("No se pudo verificar la existencia del usuario en la base de datos", e);
        }

        return false;
    }

    public boolean existsEmail(String email) {
        String query = "SELECT COUNT(*) FROM Usuario WHERE correo_electronico = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("No se pudo verificar la existencia del del email en la base de datos", e);
        }

        return false;
    }

    public boolean validateLogin(String username, String password) {

        String query = "SELECT id_usuario, hash_contrasena FROM Usuario WHERE nombre = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    String hashFromDB = rs.getString("hash_contrasena");

                    if (EncryptPassword.verify(password, hashFromDB)) {
                        this.user = findById(id);
                        AuditUtil.registrar(user, "Se conectó al sistema", AuditType.LOGIN);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            log.error("No se pudo verificar credenciales del usuario en la base de datos", e);
        }
        return false;
    }
    
    @Override
    public User findById(int id) {
        String query = "SELECT * FROM Usuario WHERE id_usuario = ?";

        try (PreparedStatement stm = getConnection().prepareStatement(query)) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("nombre");
                    String hash = rs.getString("hash_contrasena");
                    String email = rs.getString("correo_electronico");
                    UserType type = UserType.fromId(rs.getInt("id_tipo_usuario"));
                    LocalDateTime createdAt= rs.getTimestamp("fecha_registro").toLocalDateTime();
                    return new User(id, username, hash, email, type,createdAt);
                }
            }
        } catch (SQLException e) {
            log.error("No se pudo obtener usuario en la base de datos ", e);
        }
        return null;
    }
    
    @Override
    public boolean save(User entity, User u) {
        String query = "INSERT INTO Usuario (nombre, hash_contrasena, correo_electronico, id_tipo_usuario) VALUES (?,?,?,?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getEmail());
            stmt.setInt(4, entity.getType().getId());
            stmt.executeUpdate();
           
            AuditUtil.registrar(u, "Creó nuevo usuario: "+ entity.getUsername(), AuditType.CREACION);

            return true;
        } catch (SQLException ex) {
            log.error("No se pudo registrar usuario en la base de datos", ex);
        }
        return false;
    }

    @Override
    public boolean delete(int id, User u) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            AuditUtil.registrar(u, "Eliminó el usuario USU-"+id, AuditType.ELIMINACION);
            return true;
        } catch (SQLException e) {
            log.error("No se pudo elimar usuario en la base de datos", e);
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (PreparedStatement stm = getConnection().prepareStatement(sql); 
                ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("nombre"));
                u.setEmail(rs.getString("correo_electronico"));
                u.setPassword(rs.getString("hash_contrasena"));
                u.setType(UserType.fromId(rs.getInt("id_tipo_usuario")));
                u.setCreatedAt(rs.getTimestamp("fecha_registro").toLocalDateTime());
                lista.add(u);
            }

        } catch (SQLException e) {
            log.error("No se pudo listar usuarios de la base de datos", e);
        }

        return lista;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public boolean uptade(User entity, User u) {
        String sql = "UPDATE Usuario SET nombre=?, hash_contrasena=?, correo_electronico=?, id_tipo_usuario=? WHERE id_usuario=?";
      
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.setInt(4, entity.getType().getId());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
            AuditUtil.registrar(u, "Actualizó el usuario USU-"+entity.getId(), AuditType.MODIFICACION);
            return true;
        } catch (SQLException e) {
            log.error("No se pudo actualizar usuario en la base de datos", e);
            return false;
        }
    }

}
