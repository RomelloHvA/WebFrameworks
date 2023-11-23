package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;
import app.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class TripsRepositoryJpa2 implements TripRepository<Trip>{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Trip> findAll() {
        TypedQuery<Trip> query = this.entityManager.createQuery("select t from Trip t", Trip.class);
        return query.getResultList();
    }

    @Override
    public Trip findById(long id) throws ResourceNotFound {
        try {
            Trip trip = entityManager.find(Trip.class, id);

            if (trip == null) {
                throw new ResourceNotFound("Trip not found with id: " + id);
            }
            return trip;
        } catch (Exception e) {
            throw new ResourceNotFound("Error findin Trip: " + id, e);
        }
    }

    @Override
    @Transactional
    public Trip save(Trip trip) throws ResourceNotFound {
        if (trip == null) {
            throw new ResourceNotFound("Trip cannot be null for Save method");
        }

        Trip temp = new Trip();

        try {
            temp = findById(trip.getId());
        }catch (Exception e) {
            System.out.println("Trip doesnt exist. Creating new one");
            temp.setId(0L);
        }

        updateTripFields(temp, trip);
        temp.setScooter(trip.getScooter());

        // If the trip has an ID, check if it already exists in the database
        System.out.println("Trying to save or update trip");
        return entityManager.merge(temp);
    }


    // Helper method to update fields of an existing trip
    private void updateTripFields(Trip existingTrip, Trip newTrip) {
        existingTrip.setStartTime(newTrip.getStartTime());
        existingTrip.setEndTime(newTrip.getEndTime());
        existingTrip.setStartPosition(newTrip.getStartPosition());
        existingTrip.setEndPosition(newTrip.getEndPosition());
        existingTrip.setMileage(newTrip.getMileage());
        existingTrip.setCost(newTrip.getCost());
        // Update other fields as needed
    }

    @Override
    public Trip deleteById(long id) throws ResourceNotFound {
        Trip trip = entityManager.find(Trip.class, id);

        if (trip == null) {
            throw new ResourceNotFound("Trip not found to delete for id: " + id);
        }
        try {
            entityManager.remove(trip);
            return trip;
        } catch (Exception e){
            throw new ResourceNotFound("Error deleting trip with id: " + id, e);
        }
    }
}
