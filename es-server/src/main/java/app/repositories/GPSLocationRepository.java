package app.repositories;

import app.models.GPSLocation;
import app.models.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GPSLocationRepository extends JpaRepository<GPSLocation, Long> {
    Optional<GPSLocation> findByScooter_Id(Long id);
    @Modifying
    @Query("UPDATE GPSLocation g SET g.latitude = :latitude, g.longitude = :longitude WHERE g.id = :id")
    void updateLatitudeAndLongitudeForScooter(@Param("id") Long id,
                                              @Param("latitude") Double latitude,
                                              @Param("longitude") Double longitude);

    void deleteAllByScooter_Id(Long id);
}
