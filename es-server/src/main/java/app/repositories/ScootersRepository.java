package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;

import java.util.List;

public interface ScootersRepository<E> {
    List<E> findAll();// finds all available instances

    List<Scooter> findByQuery(String jpqlName, Object... params);
    Scooter findById(long id) throws ResourceNotFound;
    Scooter save(Scooter scooter) throws ResourceNotFound;
    Scooter deleteById(long id) throws ResourceNotFound;
}
