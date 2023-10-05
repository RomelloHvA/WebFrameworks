package app.rest;

import app.models.Scooter;
import app.repositories.ScootersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
