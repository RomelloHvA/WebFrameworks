package app.repositories;

import app.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripsRepositoryJpa extends JpaRepository<Trip, Long> {

    List<Trip> findAllByScooter_Id(Long id);

}
