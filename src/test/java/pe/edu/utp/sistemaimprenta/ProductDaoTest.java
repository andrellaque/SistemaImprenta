package pe.edu.utp.sistemaimprenta;

import org.junit.jupiter.api.Test;
import pe.edu.utp.sistemaimprenta.model.Product;
import pe.edu.utp.sistemaimprenta.model.ProductType;
import pe.edu.utp.sistemaimprenta.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import pe.edu.utp.sistemaimprenta.dao.ProductDao;

public class ProductDaoTest {

    private final ProductDao dao = new ProductDao();

    @Test
    void testGuardarProducto() {
        Product p = new Product();
        p.setName("Producto Prueba");
        p.setDescription("Descripción de prueba");
        p.setType(ProductType.fromId(1)); // Debe existir en BD
        p.setBasePrice(10.50);
        p.setActive(true);

        User u = new User();
        u.setId(1);

        boolean resultado = dao.save(p, u);
        assertTrue(resultado);
    }

    @Test
    void testBuscarProductoPorId() {
        Product p = dao.findById(1);
        assertNotNull(p);
    }

    @Test
    void testBuscarIdInexistente() {
        Product p = dao.findById(99999);
        assertNull(p);
    }

    @Test
    void testListarProductos() {
        List<Product> lista = dao.findAll();
        assertNotNull(lista);
    }

    @Test
    void testActualizarProducto() {
        Product p = dao.findById(1);
        if (p != null) {
            p.setName("Prod Actualizado");
            User u = new User();
            u.setId(1);

            boolean resultado = dao.update(p, u);
            assertTrue(resultado);
        }
    }

    @Test
    void testEliminarProducto() {
        User u = new User();
        u.setId(1);

        boolean resultado = dao.delete(1, u);
        assertTrue(resultado);
    }
}

