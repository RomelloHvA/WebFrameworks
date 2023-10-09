package app.repositories;

import app.models.Scooter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("SCOOTERS.INMEMORY")
public class ScootersRepositoryMock implements ScootersRepository{

    private List<Scooter> scooters;

    public ScootersRepositoryMock() {
        int numberOfScooters = 7;
        int startId = 4000;
        scooters = new ArrayList<>();

        for (int i = 0; i < numberOfScooters; i++) {
            if (!idExists(startId + i)) {
                scooters.add(Scooter.createSampleScooter(startId+i));
            }
        }
    }

    /**
     * Method for finding a given Id. This method should be implemented whenever a scooter is added to the list.
     * @param idToFind is the Id to find before being able to add to the scooterslist
     * @return if the id exists.
     * @author Romello ten Broeke
     */
    public boolean idExists(int idToFind){
        for (Scooter scooter : scooters){
            if (scooter.getId() == idToFind) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List findAll() {
        return scooters;
    }

}
