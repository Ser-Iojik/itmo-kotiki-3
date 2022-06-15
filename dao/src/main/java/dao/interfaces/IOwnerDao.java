package dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IOwnerDao<E, Id extends Serializable> {

    public E persist(E entity);

    public E update(E entity);

    public E findById(Id id);

    public List<E> findAll();

    public void delete(E entity);

    public void deleteAll();
}