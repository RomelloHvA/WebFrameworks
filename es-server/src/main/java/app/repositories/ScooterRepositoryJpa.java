package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.GPSLocation;
import app.models.Scooter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class ScooterRepositoryJpa implements ScootersRepository<Scooter> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GPSLocationRepository gpsLocationRepository;
    @Override
    public List<Scooter> findAll() {
        System.out.println("Jpa repository is working");
        TypedQuery<Scooter> query = this.entityManager.createQuery("select s from Scooter s", Scooter.class);
        return query.getResultList();
    }

    @Override
    public Scooter findById(long id) throws ResourceNotFound {
        try {
            Scooter scooter = entityManager.find(Scooter.class, id);

            if (scooter == null) {
                throw new ResourceNotFound("Scooter not found with id: " + id);
            }

            return scooter;
        } catch (Exception e) {
            throw new ResourceNotFound("Error finding Scooter with id: " + id, e);
        }
    }


    @Override
    @Transactional
    public Scooter save(Scooter scooter) throws ResourceNotFound {
        Scooter temp = new Scooter();
        try {
            System.out.println(scooter.getGpsLocation());

            if (entityManager.contains(scooter)) {
               scooter = entityManager.merge(scooter);
            }
            // Save the Scooter
            if (scooter.getId() == 0) {

                temp.setGpsLocation(scooter.getGpsLocation());
                temp.setStatus(scooter.getStatus());
                temp.setMileage(scooter.getMileage());
                temp.setTag(scooter.getTag());
                temp.setBatteryCharge(scooter.getBatteryCharge());
                entityManager.persist(temp);

                GPSLocation tempGps = new GPSLocation();
                tempGps.setScooter(temp);
                tempGps.setLatitude(scooter.getGpsLocation().getLatitude());
                tempGps.setLongitude(scooter.getGpsLocation().getLongitude());
                gpsLocationRepository.save(tempGps);

            }

//            // Check if GPSLocation is provided in the Scooter entity
//            if (scooter.getGpsLocation() != null) {
//                GPSLocation gpsLocation = scooter.getGpsLocation();
//
//                // Set the reference in the GPSLocation entity
//                gpsLocation.setScooter(scooter);
//
//                // Save or update the GPSLocation entity
//                gpsLocationRepository.save(gpsLocation);
//            }

            return temp;
        } catch (Exception e) {
            // Handle other exceptions if needed
            throw new ResourceNotFound("Error saving Scooter", e);
        }
    }




    @Override
    @Transactional
    public Scooter deleteById(long id) throws ResourceNotFound {
        Scooter scooter = entityManager.find(Scooter.class, id);
        if (scooter == null) {
            throw new ResourceNotFound("Scooter not found with id: " + id);
        }
        try {
            entityManager.remove(scooter);
            return scooter;
        } catch (Exception e) {
            throw new ResourceNotFound("Error deleting Scooter with id: " + id, e);
        }
    }

}
