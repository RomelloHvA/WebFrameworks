package app.repositories;

import app.models.Scooter;

import java.util.List;

public interface ScootersRepository<E> {
    List<E> findAll();// finds all available instances
    Scooter findById(long id);
    Scooter save(Scooter scooter);
    Scooter deleteById(long id);
}
