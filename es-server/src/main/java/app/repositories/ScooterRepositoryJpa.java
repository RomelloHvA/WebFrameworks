package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.GPSLocation;
import app.models.Scooter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ScooterRepositoryJpa implements ScootersRepository<Scooter> {

    @PersistenceContext
    private EntityManager entityManager;

    @Component
    public class MyCommandLineRunner  implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            System.out.println("Filling in some test data");
            createInitialScooters();
        }
    }

    private void createInitialScooters() throws ResourceNotFound {
        List<Scooter> scooters = findAll();
        if (scooters.size() > 0) {
            System.out.println("There are already scooters in the test data");
            return;
        }
        System.out.println("Generating scooters");
        for (int i = 0; i < 11; i++) {
            Scooter scooter = Scooter.createSampleScooter(0);
            Scooter savedScooter = save(scooter);
            System.out.println("Created scooter with id: " + savedScooter.getId());
        }
    }

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
        if (scooter == null) {
            throw new ResourceNotFound("Scooter cannot be null for Save method");
        }
        //See if the scooter can be found.
        try {
            temp = findById(scooter.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
            temp.setId(0);
        }

        temp.setTag(scooter.getTag());
        temp.setBatteryCharge(scooter.getBatteryCharge());
        temp.setMileage(scooter.getMileage());
        temp.setStatus(scooter.getStatus());
// Check for a GPS to update otherwise make a new one.
        Optional<GPSLocation> tempGPS = gpsLocationRepository.findByScooter_Id(temp.getId());
        if (tempGPS.isPresent()){
            gpsLocationRepository.updateLatitudeAndLongitudeForScooter(
                    temp.getGpsLocation().getId(),
                    scooter.getGpsLocation().getLatitude(),
                    scooter.getGpsLocation().getLongitude());
            temp.setGpsLocation(tempGPS.get());
        } else {
            temp.setGpsLocation(scooter.getGpsLocation());
            temp.getGpsLocation().setScooter(temp);
        }
        return entityManager.merge(temp);
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
