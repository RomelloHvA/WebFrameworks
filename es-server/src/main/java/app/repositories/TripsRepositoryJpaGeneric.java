package app.repositories;

import app.models.Trip;
import org.springframework.stereotype.Repository;

@Repository("TRIPS.JPA")
public class TripsRepositoryJpaGeneric extends AbstractEntityRepositoryJpa<Trip>{

    public TripsRepositoryJpaGeneric() {
        super(Trip.class);
    }
}
