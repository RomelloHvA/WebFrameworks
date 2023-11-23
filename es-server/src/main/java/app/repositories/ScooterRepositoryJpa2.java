package app.repositories;

import app.models.Scooter;
import org.springframework.stereotype.Repository;

@Repository("SCOOTERS.JPA")
public class ScooterRepositoryJpa2 extends AbstractEntityRepositoryJpa<Scooter> {

        public ScooterRepositoryJpa2() {
            super(Scooter.class);
        }
}
