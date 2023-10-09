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

    @Override
    public Scooter save(Scooter scooter) {
        return null;
    }

    @Override
    public Scooter deleteById(long id) {
        if (findById(id) != null){
            scooters.remove(findById(id));
        }
        // Cant find the id
        return null;
    }

}
