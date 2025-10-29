package pe.edu.utp.sistemaimprenta.dao;
import java.util.List;
import pe.edu.utp.sistemaimprenta.model.User;

public interface CrudDao<T> {

    boolean save(T entity,User e);

    T findById(int id);

    boolean delete(int id,User e);
    
    boolean update(T entity, User e);
    
    List<T> findAll();
}
