package th.ac.ku.kps.eng.cpe.soa.driveCar.dao;
import java.io.Serializable;
import java.util.List;

 
public interface DaoInterface<T, Id extends Serializable> {
 
    public boolean  persist(T entity);
     
    public boolean update(T entity);
     
    public T findById(Id id);
     
    public boolean delete(T entity);
     
    public List<T> findAll();
     
    public void deleteAll();
     
}
