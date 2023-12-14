package app.rest;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
import app.models.GPSLocation;
import app.models.Scooter;
import app.models.Trip;
import app.repositories.*;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing scooters.
 *
 * This controller handles HTTP requests related to scooter management.
 */
@RestController
@RequestMapping("/scooters")
public class ScooterController {

    @Autowired
//    ScootersRepository<Scooter> scootersRepo;
    ScooterRepositoryJpa scootersRepo;

    @Autowired
//    TripsRepositoryJpa tripsRepositoryJpa;
    TripsRepositoryJpa2 tripsRepositoryJpa;

    @Autowired
    AbstractEntityRepositoryJpa<GPSLocation> gpsLocationRepositoryJpa;


    /**
     * Get a list of test scooters.
     *
     * @return A list of test scooters in JSON format
     * @author Marco de Boer
     */
    @GetMapping(path = "test", produces = "application/json")
    public List<Scooter> getTestScooters() {
        return List.of(
                new Scooter("Test-scooter-A"),
                new Scooter("Test-scooter-B")
        );
    }

    /**
     * Get all scooters.
     *
     * @return A list of all scooters in JSON format
     * @author Marco de Boer
     */
    @GetMapping(path = "all",produces = "application/json")
    public ResponseEntity<?> getAllScooters(
            @RequestParam(name = "battery", required = false) Integer battery,
            @RequestParam(name = "status", required = false) String status) {
        if (battery != null) {
            return ResponseEntity.ok(scootersRepo.findByQuery("Scooter_find_by_battery", battery));
        } else if (status != null) {
            try {
                Scooter.Status statusEnum = Scooter.Status.valueOf(status.toUpperCase());
                return ResponseEntity.ok(scootersRepo.findByQuery("Scooter_find_by_status", statusEnum));

            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid status value: " + status);
            }
        } else {
            return ResponseEntity.ok(scootersRepo.findAll());
        }
    }

    /**
     * Get all scooters that are currently available.
     * @param id The ID of the scooter to find
     * @param from The start date of the period to find trips for
     * @param to The end date of the period to find trips for
     * @return A list of all trips for the scooter with the given ID in JSON format
     * @author Marco de Boer
     */
    @GetMapping(path = "{id}/trips", produces = "application/json")
    public ResponseEntity<Object> getTripsByScooterId (
            @PathVariable long id,
            @RequestParam(name = "from", required = false) LocalDate from,
            @RequestParam(name = "to", required = false) LocalDate to)
    {
        if (from != null && to != null) {
            return ResponseEntity.ok(tripsRepositoryJpa.findByQuery("Trip_find_by_scooterId_and_period", id, from, to));
        }

        return ResponseEntity.ok(tripsRepositoryJpa.findAllByScooter_Id(id));
    }

    /**
     * Find a scooter by its ID.
     *
     * @param id The ID of the scooter to find
     * @return The scooter with the given ID in JSON format
     * @throws ResourceNotFound if the scooter with the specified ID is not found
     * @author Romello ten Broeke
     */
    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable long id) throws ResourceNotFound {
        Scooter scooter = scootersRepo.findById(id);
        if (scooter != null) {
            return ResponseEntity.ok(scooter);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "summary", produces = "application/json")
    @JsonView(Scooter.Views.Summary.class)
    public List<Scooter> getScootersSummary() { return scootersRepo.findAll(); }

    /**
     * Add a new scooter.
     *
     * @param id      The ID of the scooter to add
     * @param scooter The scooter to add
     * @return The added scooter in JSON format
     * @throws PreConditionFailed if the scooter ID in the URL doesn't match the given scooter ID
     * @author Romello ten Broeke
     */
    @PostMapping("{id}")
    @Transactional
    public ResponseEntity<Object> addNewScooter(@PathVariable long id, @RequestBody Scooter scooter) throws PreConditionFailed, ResourceNotFound {
        if (id != scooter.getId()){
            throw new PreConditionFailed("ID in URL doesn't match the given scooter ID");
        } else {
//            if(scooter.getGpsLocation() != null){
//                GPSLocation gpsLocation = scooter.getGpsLocation();
////                gpsLocation = gpsLocationRepositoryJpa.save(gpsLocation);
////                scooter.setGpsLocation(gpsLocation);
//                System.out.println(gpsLocation);
////                System.out.println(scooter);
//            }
            return ResponseEntity.status(HttpStatus.CREATED).body(scootersRepo.save(scooter));
        }
    }

    @PostMapping("{id}/trips")
    public ResponseEntity<Object> addNewTrip(
            @PathVariable long id,
            @RequestBody Trip trip){
        Scooter scooter = new Scooter();
        try {
            scooter = scootersRepo.findById(id);

            if (scooter.getStatus() != Scooter.Status.IDLE || scooter.getBatteryCharge() < 10){
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Scooter cannot be Idle or below 10% battery");
            }

            if (scooter.associateTrip(trip)){
                tripsRepositoryJpa.save(trip);
            }

        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Trip has been added");
    }

    /**
     * Update a scooter.
     *
     * @param id      The ID of the scooter to update
     * @param scooter The updated scooter
     * @return The updated scooter in JSON format
     * @throws PreConditionFailed if the scooter ID in the URL doesn't match the given scooter ID
     * @author Romello ten Broeke
     */
    @PutMapping("{id}")
    public ResponseEntity<Object> updateScooter(@PathVariable long id, @RequestBody Scooter scooter) throws PreConditionFailed, ResourceNotFound {

        if (id != scooter.getId()){
            throw new PreConditionFailed("ID in URL doesn't match the given scooter ID");
        }
        //Scooter is found and will be updated
        if (scootersRepo.findById(id) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(scootersRepo.save(scooter));
            //New scooter is created and added
        } else if (scootersRepo.findById(id) == null) {
            return addNewScooter(id,scooter);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found scooter with ID:" + scooter.getId());
        }
    }

    /**
     * Delete a scooter by its ID.
     *
     * @param id The ID of the scooter to delete
     * @return Response indicating the success of the deletion
     * @author Romello ten Broeke
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteScooter(@PathVariable long id) throws ResourceNotFound {
        if (scootersRepo.findById(id) == null) {
            Exception exception = new ResourceNotFound("Id not found:" +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok(scootersRepo.deleteById(id));
    }
}

