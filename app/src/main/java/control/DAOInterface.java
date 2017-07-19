package control;

import java.util.List;

/**
 * Created by saran on 12/07/17.
 */

public interface DAOInterface<T> {

    long save(T type);
    void update(T type);
    void delete(T type);
    T get(long id);
    List<T> getAll();


}
