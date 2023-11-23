//package app.rest;
//
//import app.models.Scooter;
//import app.models.Trip;
//import app.repositories.AbstractEntityRepositoryJpa;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/scooters")
//public class ScooterControllerGeneric {
//
//    @Autowired
//    AbstractEntityRepositoryJpa<Scooter> scootersRepo;
//
//    @Autowired
//    AbstractEntityRepositoryJpa<Trip> tripsRepo;
//
//    @GetMapping(path = "all",produces = "application/json")
//    public List<Scooter> getAllScooters() {
//        return scootersRepo.findAll();
//    }
//}
