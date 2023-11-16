package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;

import java.util.List;

public interface ScootersRepository<E> {
    List<E> findAll();// finds all available instances
    Scooter findById(long id) throws ResourceNotFound;
    Scooter save(Scooter scooter) throws ResourceNotFound;
    Scooter deleteById(long id) throws ResourceNotFound;
}
