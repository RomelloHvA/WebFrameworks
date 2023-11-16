package app.repositories;

import app.models.GPSLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPSLocationRepository extends JpaRepository<GPSLocation, Long> {

}
