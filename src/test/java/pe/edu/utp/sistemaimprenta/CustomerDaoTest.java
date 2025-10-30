package pe.edu.utp.sistemaimprenta;

import org.junit.jupiter.api.Test;
import pe.edu.utp.sistemaimprenta.model.Customer;
import pe.edu.utp.sistemaimprenta.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import pe.edu.utp.sistemaimprenta.dao.CustomerDao;

public class CustomerDaoTest {

    private final CustomerDao dao = new CustomerDao();

    @Test
    void testGuardarCliente() {
        Customer c = new Customer();
        c.setDni("99999999");
        c.setLastName("Prueba");
        c.setName("Cliente");
        c.setTelephoneNumber("987654321");
        c.setEmail("prueba@test.com");
        c.setAddress("Lima");

        User u = new User();
        u.setId(1);

        boolean resultado = dao.save(c, u);
        assertTrue(resultado);
    }

    @Test
    void testBuscarPorId() {
        Customer c = dao.findById(1);
        assertNotNull(c);
    }

    @Test
    void testBuscarIdNoExistente() {
        Customer c = dao.findById(99999);
        assertNull(c);
    }

    @Test
    void testListarClientes() {
        List<Customer> lista = dao.findAll();
        assertNotNull(lista);
    }

    @Test
    void testActualizarCliente() {
        Customer c = dao.findById(1);
        if (c != null) {
            c.setName("Actualizado");
            User u = new User();
            u.setId(1);

            boolean resultado = dao.update(c, u);
            assertTrue(resultado);
        }
    }

    @Test
    void testEliminarCliente() {
        User u = new User();
        u.setId(1);

        boolean resultado = dao.delete(1, u);
        assertTrue(resultado);
    }
}
