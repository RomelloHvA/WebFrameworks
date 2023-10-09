package app.rest;

import app.models.Scooter;
import app.repositories.ScootersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scooters")
public class ScooterController {


    @Autowired
    ScootersRepository<Scooter> scootersRepo;



    @GetMapping(path = "test", produces = "application/json")
    public List<Scooter> getTestScooters() {
        return List.of(
                new Scooter("Test-scooter-A"),
                new Scooter("Test-scooter-B")
        );
    }

    @GetMapping(path = "all", produces = "application/json")
    public List<Scooter> getAllScooters() {
        return scootersRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable long id){
        Scooter scooter = scootersRepo.findById(id);
        if (scooter != null) {
            return ResponseEntity.ok(scooter);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }
}
