package app;

import app.exceptions.ResourceNotFound;
import app.models.Scooter;
import app.models.Trip;
import app.repositories.GPSLocationRepository;
import app.repositories.ScooterRepositoryJpa;
import app.repositories.TripsRepositoryJpa2;
import app.rest.ScooterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class EsServerApplication implements CommandLineRunner {

	@Autowired
	private TripsRepositoryJpa2 tripsRepositoryJpa2;

	@Autowired
	ScooterRepositoryJpa scooterRepositoryJpa;

	@Autowired
	GPSLocationRepository gpsLocationRepository;

	public static void main(String[] args) {
		SpringApplication.run(EsServerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8080")
						.allowedMethods("GET", "POST","PUT", "DELETE")
						.allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
						.exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
						.allowCredentials(true);
			}
		};
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("The commander is running");
		createInitialScooters();
	}

	private void createInitialScooters() throws ResourceNotFound {
		List<Scooter> scooters = scooterRepositoryJpa.findAll();
		if (scooters.size() > 0) {
			System.out.println("There are already scooters in the test data");
			return;
		}
		System.out.println("Generating scooters");
		for (int i = 1; i < 11; i++) {
			Scooter scooter = Scooter.createSampleScooter(i);
			Scooter savedScooter = scooterRepositoryJpa.save(scooter);
			System.out.println("Created scooter with id: " + savedScooter.getId());

			for (int j = 0; j < 5; j++) {
				Trip trip = Trip.createSampleTrip();
				if(j%2 == 0){
					trip.setEndPosition(null);
				}
				if (scooter.associateTrip(trip)){
					tripsRepositoryJpa2.save(trip);
				}
			}


		}
	}
}
