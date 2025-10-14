
package pe.edu.utp.sistemaimprenta.dao;


import pe.edu.utp.sistemaimprenta.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.sistemaimprenta.db.DBConnection;

public class CustomerDao implements CRUDDao<Customer> {

    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }
    
    @Override
    public boolean save(Customer c) {
        String sql = "INSERT INTO Cliente (dni, apellidos, nombres, telefono, correo_electronico, direccion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getName());
            ps.setString(4, c.getTelephoneNumber());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM Cliente WHERE id_cliente=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("id_cliente"));
                    c.setDni(rs.getString("dni"));
                    c.setLastName(rs.getString("apellidos"));
                    c.setName(rs.getString("nombres"));
                    c.setTelephoneNumber(rs.getString("telefono"));
                    c.setEmail(rs.getString("correo_electronico"));
                    c.setAddress(rs.getString("direccion"));
                    c.setCreatedAt(rs.getTimestamp("fecha_registro").toLocalDateTime());
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean uptade(Customer c) {  
        String sql = "UPDATE Cliente SET dni=?, apellidos=?, nombres=?, telefono=?, correo_electronico=?, direccion=? " +
                     "WHERE id_cliente=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getName());
            ps.setString(4, c.getTelephoneNumber());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getAddress());
            ps.setInt(7, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Statement st = getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id_cliente"));
                c.setDni(rs.getString("dni"));
                c.setLastName(rs.getString("apellidos"));
                c.setName(rs.getString("nombres"));
                c.setTelephoneNumber(rs.getString("telefono"));
                c.setEmail(rs.getString("correo_electronico"));
                c.setAddress(rs.getString("direccion"));
                c.setCreatedAt(rs.getTimestamp("fecha_registro").toLocalDateTime());
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }
}

