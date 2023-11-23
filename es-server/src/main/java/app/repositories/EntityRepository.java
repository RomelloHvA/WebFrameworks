package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Identifiable;

import java.util.List;

public interface EntityRepository <E extends Identifiable> {
    List<E> findAll();
    E findById(long id) throws ResourceNotFound;
    E save(E entity) throws ResourceNotFound;
    boolean deleteById(long id) throws ResourceNotFound;
}
