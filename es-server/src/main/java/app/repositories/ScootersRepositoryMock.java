package app.repositories;

import app.models.Scooter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository("SCOOTERS.INMEMORY")
public class ScootersRepositoryMock implements ScootersRepository{

    private List<Scooter> scooters;

    public ScootersRepositoryMock() {
        int numberOfScooters = 7;
        int startId = 4000;
        scooters = new ArrayList<>();

        for (int i = 0; i < numberOfScooters; i++) {
            if (findById(startId+i) == null) {
                scooters.add(Scooter.createSampleScooter(startId+i));
            }
        }
    }



    @Override
    public List findAll() {
        return scooters;
    }
    /**
     * Method for finding a given Id. This method should be implemented whenever a scooter is added to the list.
     * @param id is the Id to find before being able to add to the scooterslist
     * @return if the id exists.
     * @author Romello ten Broeke
     */
    @Override
    public Scooter findById(long id) {
        for (Scooter scooter : scooters){
            if (scooter.getId() == id){
                return scooter;
            }
        }
        //No scooter exists by this id
        return null;
    }

    /**
     * Method for saving or editing an existing scooter. This method first checks if scooter exists by checking a
     * corresponding ID. If one is found edit the found one with the new values. If the id is 0 generate an unique id.
     * At the end it adds the new scooter if the id cant be found or if it is 0.
     * @param scooter to be saved or edited.
     * @return the scooter that was saved or edited.
     * @author Romello ten Broeke
     */

    @Override
    public Scooter save(Scooter scooter) {
        Scooter scooterToEdit = findById(scooter.getId());
        // Update the scooter if it is found in the list
        if (scooterToEdit != null){
            scooterToEdit.setId(scooter.getId());
            scooterToEdit.setTag(scooter.getTag());
            scooterToEdit.setStatus(scooter.getStatus());
            scooterToEdit.setMileage(scooter.getMileage());
            scooterToEdit.setGPSLocation(scooter.getGpsLocation());
            scooterToEdit.setBatteryCharge(scooter.getBatteryCharge());
            //Returns the changed scooter.
            return scooterToEdit;

        } else if (scooter.getId() == 0) {
            long newId = generateUniqueId();
            while (findById(newId) != null){
                newId = generateUniqueId();
            }
            scooter.setId(newId);
        }
        scooters.add(scooter);
        return scooter;
    }

    /**
     * Random number generotor for the ID
     * @return a random number.
     * @author Romello ten Broeke
     */
    private long generateUniqueId(){
        return new Random().nextLong();
    }

    /**
     * Deletes scooters by ID if one can be found.
     * @param id of the scooter to be deleted.
     * @return the deleted scooter or null if one couldn't be found
     * @author Romello ten Broeke
     */
    @Override
    public Scooter deleteById(long id) {
        // Save the scooter to a local variable so there are no unnecessary calls to findyById.
        Scooter scooterToDelete = findById(id);
        if (scooterToDelete != null){
            scooters.remove(scooterToDelete);
        }
        // Cant find the id so it returns null.
        return scooterToDelete;
    }

}
