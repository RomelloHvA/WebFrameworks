package app.repositories;

import app.models.Scooter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SCOOTERS.INMEMORY")
public class ScootersRepositoryMock implements ScootersRepository{

    private List<Scooter> scooters;

    public ScootersRepositoryMock() {
        scooters = List.of(
                Scooter.createSampleScooter(3001),
                Scooter.createSampleScooter(3002),
                Scooter.createSampleScooter(3003),
                Scooter.createSampleScooter(3004),
                Scooter.createSampleScooter(3005),
                Scooter.createSampleScooter(3006),
                Scooter.createSampleScooter(3007)
        );
    }

    @Override
    public List findAll() {
        return scooters;
    }

}
