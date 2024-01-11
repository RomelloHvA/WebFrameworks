package app.rest;

import app.exceptions.ResourceNotFound;
import app.models.GPSLocation;
import app.models.Trip;
import app.repositories.TripsRepositoryJpa2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripsController {

    @Autowired
    TripsRepositoryJpa2 tripsRepositoryJpa;

    @GetMapping
    public List<Trip> getTrips() {
        return tripsRepositoryJpa.findAll();

    }

    @PutMapping("/{id}/end")
    public ResponseEntity<Trip> endTrip(@PathVariable long id) throws ResourceNotFound {
        Trip trip = tripsRepositoryJpa.findById(id);
        trip.setEndPosition(GPSLocation.createRandomGPSLocation());
        tripsRepositoryJpa.save(trip);
        return ResponseEntity.ok(trip);
    }

}
