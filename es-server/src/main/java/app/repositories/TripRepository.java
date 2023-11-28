package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;
import app.models.Trip;

import java.util.List;

public interface TripRepository<E> {
    List<E> findAll();// finds all available instances

    List<Trip> findByQuery(String jpqlName, Object... params);

    List<Trip> findAllByScooter_Id(Long id);
    Trip findById(long id) throws ResourceNotFound;
    Trip save(Trip trip) throws ResourceNotFound;
    Trip deleteById(long id) throws ResourceNotFound;
}
