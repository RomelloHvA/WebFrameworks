package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "Trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "startTime")
    private LocalDate startTime;
    @Column(name ="endTime")
    private LocalDate endTime;
    @Column(name = "startPosition")
    private String startPosition;
    @Column(name = "endPosition")
    private String endPosition;
    @Column(name = "mileage")
    private int mileage;
    @Column(name ="cost")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "scooter_id")
    @JsonBackReference
    private Scooter scooter;

    public static Trip createSampleTrip(){
        final Random RANDOM = new Random();
        LocalDate startTime = LocalDate.now().plusDays(RANDOM.nextInt(30)); // Up to 30 days in the future
        LocalDate endTime = startTime.plusDays(RANDOM.nextInt(10) + 1); // 1 to 10 days duration
        String startPosition = "Start" + (RANDOM.nextDouble(50.000));
        String endPosition = "End" + (RANDOM.nextDouble(20.000, 50.000));
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
        } else if (scooter.getStatus() == Scooter.Status.IDLE){
            System.out.println("Status = idle. trying to add new trip");
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
        System.out.println("No trip added for scooter: " + scooter.getId());
return false;


    }

    public Trip() {
    }

    public Trip(LocalDate startTime, LocalDate endTime, String startPosition, String endPosition, int mileage, double cost) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
    }

    public Trip(Long id, LocalDate startTime, LocalDate endTime, String startPosition, String endPosition, int mileage, double cost, Scooter scooter) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
        this.scooter = scooter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
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
}
