package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class ScootersRepositoryMock implements ScootersRepository{

    private List<Scooter> scooters;

    public ScootersRepositoryMock() throws ResourceNotFound {
        int numberOfScooters = 7;
        int startId = 4000;
        scooters = new ArrayList<>();

        for (int i = 0; i < numberOfScooters; i++) {
            try {
                findById(startId+i);
            } catch (ResourceNotFound e){
                System.out.println(e.getMessage());
                System.out.println("Creating Scooter with id:" + startId + i);
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
    public Scooter findById(long id) throws ResourceNotFound {
        for (Scooter scooter : scooters){
            if (scooter.getId() == id){
                return scooter;
            }
        }
        //No scooter exists by this id
        throw new ResourceNotFound("cant find Scooter with Id:" + id);
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
    public Scooter save(Scooter scooter) throws ResourceNotFound {
        try {
            Scooter scooterToEdit = findById(scooter.getId());

            // Update the scooter if it is found in the list
            if (scooterToEdit != null) {
                scooterToEdit.setId(scooter.getId());
                scooterToEdit.setTag(scooter.getTag());
                scooterToEdit.setStatus(scooter.getStatus());
                scooterToEdit.setMileage(scooter.getMileage());
                scooterToEdit.setGPSLocation(scooter.getGpsLocation());
                scooterToEdit.setBatteryCharge(scooter.getBatteryCharge());

                // Returns the changed scooter.
                return scooterToEdit;
            } else if (scooter.getId() == 0) {
                long newId = generateUniqueId();
                while (findById(newId) != null) {
                    newId = generateUniqueId();
                }
                scooter.setId(newId);
            }
            scooters.add(scooter);
            return scooter;
        } catch (ResourceNotFound e) {
            // Handle the exception (e.g., log it, print a message, etc.)
            System.out.println("Error in save method: " + e.getMessage());
            throw e; // Re-throw the exception if needed
        }
    }


    /**
     * Random number generotor for the ID
     * @return a random number.
     * @author Romello ten Broeke
     */
    private long generateUniqueId(){
        return Math.abs(new Random().nextLong());
    }

    /**
     * Deletes scooters by ID if one can be found.
     * @param id of the scooter to be deleted.
     * @return the deleted scooter or null if one couldn't be found
     * @author Romello ten Broeke
     */
    @Override
    public Scooter deleteById(long id) throws ResourceNotFound {
        // Save the scooter to a local variable so there are no unnecessary calls to findyById.
        Scooter scooterToDelete = findById(id);
        if (scooterToDelete != null){
            scooters.remove(scooterToDelete);
        }
        // Cant find the id so it returns null.
        return scooterToDelete;
    }

}
