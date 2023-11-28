package app.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name= "Scooter")
@NamedQuery(name = "Scooter_find_by_status", query = "SELECT s FROM Scooter s WHERE s.status = ?1")
@NamedQuery(name = "Scooter_find_by_battery", query = "SELECT s FROM Scooter s WHERE s.batteryCharge <= ?1")
public class Scooter implements Identifiable {
    @JsonView(Views.Summary.class)
    @Column(name = "tag")
    private String tag;
    @JsonView(Views.Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public void setGpsLocation(GPSLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    @JsonView(Views.Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToOne(mappedBy = "scooter", cascade = CascadeType.ALL)
    private GPSLocation gpsLocation;


    @Column(name = "mileage")
    private int mileage;
    @JsonView(Views.Summary.class) private int batteryCharge;

    @OneToMany(mappedBy = "scooter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    public Scooter() {

    }

    public boolean associateTrip(Trip trip){
        //Check if already associated and do nothing.
        if (trips.contains(trip)){
            return false;
            // If trip is not associated add to the list and change it in the trip aswell.
        } else {
            trips.add(trip);
            trip.associateScooter(this);
            return true;
        }
    }

    public boolean dissociateTrip(Trip trip){
        if (trips.contains(trip)){
            trips.remove(trip);
            return true;
        } else {
            return false;
        }
    }

    public class Views {
        public static class Summary {

        }

    }


    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     *
     * This constructor is used when parsing json to a new scooter instance.
     */

    public Scooter(long id, String tag, Status status, GPSLocation gpsLocation, int mileage, int batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    public enum Status {
        IDLE, IN_USE, MAINTENANCE, UNAVAILABLE;

        private static final Random PRNG = new Random();

        public static Status randomStatus(){
            Status[] statusses = values();
            return statusses[PRNG.nextInt(statusses.length)];
        }
    }

    public Scooter(String tag) {
        this.tag = tag;
    }

    public Scooter(long id) {
        this.id = id;
    }

    public static Scooter createSampleScooter(long id){
        Scooter scooter = new Scooter(id);
        scooter.setTag(createRandomTag(8));
        scooter.setStatus(Status.randomStatus());
        scooter.setGPSLocation(GPSLocation.createRandomGPSLocation());
        scooter.setMileage((int) Math.round(Math.random() * 10000));
        scooter.setBatteryCharge((int) Math.floor(Math.random() * 95) + 5);
        return scooter;
    }

    private static String createRandomTag(int length) {
        String result = "";
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int charactersLength = characters.length();
        int counter = 0;
        while (counter < length) {
            result += characters.charAt((int) Math.floor(Math.random() * charactersLength));
            counter += 1;
        }
        return result;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setGPSLocation(GPSLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public String getTag() {
        return tag;
    }

    public Status getStatus() {
        return status;
    }

    public GPSLocation getGpsLocation() {
        return gpsLocation;
    }

    public int getMileage() {
        return mileage;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }


    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                ", gpsLocation='" + gpsLocation + '\'' +
                ", mileage=" + mileage +
                ", batteryCharge=" + batteryCharge +
                '}';
    }


}
