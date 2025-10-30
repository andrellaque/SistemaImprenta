package pe.edu.utp.sistemaimprenta;

import org.junit.jupiter.api.Test;
import pe.edu.utp.sistemaimprenta.model.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import pe.edu.utp.sistemaimprenta.dao.OrderDao;

public class OrderDaoTest {

    private final OrderDao dao = new OrderDao();

    @Test
    void testGuardarPedido() {
        Order o = new Order();
        Customer c = new Customer();
        c.setId(1); // Debe existir en BD

        User u = new User();
        u.setId(1);

        o.setCustomer(c);
        o.setUser(u);
        o.setState(OrderState.fromId(1));
        o.setDeliveryDate(LocalDateTime.now().plusDays(5));
        o.setTotalAmount(50.00);

        // detalle mínimo
        OrderDetail d = new OrderDetail();
        Product pr = new Product();
        pr.setId(1);
        d.setProduct(pr);
        d.setQuantity(1);
        d.setUnitPrice(50.00);
        d.setSubtotal(50.00);

        o.setDetails(List.of(d));

        boolean resultado = dao.save(o, u);
        assertTrue(resultado);
    }

    @Test
    void testListarPedidos() {
        List<Order> lista = dao.findAll();
        assertNotNull(lista);
    }

    @Test
    void testBuscarPedidoPorId() {
        Order o = dao.findById(1);
        assertNotNull(o);
    }

    @Test
    void testEliminarPedido() {
        User u = new User();
        u.setId(1);

        boolean resultado = dao.delete(1, u);
        assertTrue(resultado);
    }
}

