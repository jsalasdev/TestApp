package daos;

import java.util.List;

public interface IFactoryDao {

    List<Object> getAll();

    List<Object> getById(String id);

    List<Object> save(Object obj);

    Object update(Object obj);

    Object delete(Object obj);

}
