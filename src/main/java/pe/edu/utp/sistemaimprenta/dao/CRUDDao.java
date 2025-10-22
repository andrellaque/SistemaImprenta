package pe.edu.utp.sistemaimprenta.dao;
import java.util.List;

public interface CrudDao<T> {

    boolean save(T entity);

    T findById(int id);

    boolean delete(int id);
    
    boolean uptade(T entity);
    
    List<T> findAll();
}
