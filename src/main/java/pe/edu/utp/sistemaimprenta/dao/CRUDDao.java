package pe.edu.utp.sistemaimprenta.dao;
import java.util.List;

public interface CrudDao<T> {

    boolean save(T entity,T e);

    T findById(int id);

    boolean delete(int id,T e);
    
    boolean uptade(T entity, T e);
    
    List<T> findAll();
}
