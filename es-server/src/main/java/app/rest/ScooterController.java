package app.rest;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
import app.models.Scooter;
import app.models.Trip;
import app.repositories.ScootersRepository;
import app.repositories.TripsRepositoryJpa;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ScootersRepository<Scooter> scootersRepo;

    @Autowired
    TripsRepositoryJpa tripsRepositoryJpa;


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
    public List<Scooter> getAllScooters() {
        return scootersRepo.findAll();
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
    public ResponseEntity<Object> addNewScooter(@PathVariable long id, @RequestBody Scooter scooter) throws PreConditionFailed, ResourceNotFound {
        if (id != scooter.getId()){
            throw new PreConditionFailed("ID in URL doesn't match the given scooter ID");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(scootersRepo.save(scooter));
        }
    }

//    @PostMapping("{id}/trips")
//    public ResponseEntity<Object> addNewTrip(@PathVariable long id, @RequestBody Trip trip) {
//        Scooter scooter = new Scooter();
//        try {
//            scooter = scootersRepo.findById(id);
//
//            if (scooter.getStatus() != Scooter.Status.IDLE && scooter.getBatteryCharge() < 10){
//                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Scooter cannot be Idle or below 10% battery");
//            }
//        } catch (ResourceNotFound e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//
//    }

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

