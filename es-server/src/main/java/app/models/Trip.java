package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "Trip")
@NamedQuery(name = "Trip_find_by_scooterId_and_period", query = "SELECT t FROM Trip t WHERE t.scooter.id = ?1 AND t.startTime >= ?2 AND t.endTime <= ?3")
public class Trip implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "startTime")
    private LocalDate startTime;
    @Column(name = "endTime")
    private LocalDate endTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_position_id")
    private GPSLocation startPosition;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_position")
    private GPSLocation endPosition;
    @Column(name = "mileage")
    private int mileage;
    @Column(name = "cost")
    private double cost;

    @Transient
    public boolean isActive() {
        return endPosition == null;
    }

    @ManyToOne
    @JoinColumn(name = "scooter_id")
    @JsonBackReference
    private Scooter scooter;

    public static Trip createSampleTrip() {
        final Random RANDOM = new Random();
        LocalDate startTime = LocalDate.now().plusDays(RANDOM.nextInt(30)); // Up to 30 days in the future
        LocalDate endTime = startTime.plusDays(RANDOM.nextInt(10) + 1); // 1 to 10 days duration
        GPSLocation startPosition = GPSLocation.createRandomGPSLocation();
        GPSLocation endPosition =  GPSLocation.createRandomGPSLocation();
        int mileage = RANDOM.nextInt(100);
        double cost = RANDOM.nextDouble() * 50.0; // Up to $50.0

        // Create a new trip with random values
        // Associate the trip with the provided scooter
        return new Trip(startTime, endTime, startPosition, endPosition, mileage, cost);
    }


    public boolean associateScooter(Scooter scooter) {

        if (scooter == null) {
            this.scooter.dissociateTrip(this);
            this.scooter = null;
            return false;
        }
        if (scooter.equals(this.scooter)) {
            return false;
            //Associate the scooter with this trip.
        } else {
            this.scooter = scooter;
            this.scooter.associateTrip(this);
            System.out.println("Trip added for id: " + scooter.getId());
            return true;
        }

    }

    public Trip() {
    }

    public Trip(LocalDate startTime, LocalDate endTime, GPSLocation startPosition, GPSLocation endPosition, int mileage, double cost) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
    }

    public Trip(Long id, LocalDate startTime, LocalDate endTime, GPSLocation startPosition, GPSLocation endPosition, int mileage, double cost, Scooter scooter) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
        this.scooter = scooter;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public GPSLocation getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(GPSLocation startPosition) {
        this.startPosition = startPosition;
    }

    public GPSLocation getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(GPSLocation endPosition) {
        this.endPosition = endPosition;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    @Override
    public String toString() {
        return String.format("This trip belongs to scooter id: %d", scooter.getId());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
