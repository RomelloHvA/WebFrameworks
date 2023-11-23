package app.repositories;

import app.models.GPSLocation;
import org.springframework.stereotype.Repository;

@Repository("GPS.JPA")
public class GPSRepositoryJpa extends AbstractEntityRepositoryJpa<GPSLocation>{
    public GPSRepositoryJpa() {
        super(GPSLocation.class);
    }
}
