package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "GPSLocation")
public class GPSLocation implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scooter_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Scooter scooter;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;

    public GPSLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GPSLocation() {

    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude (double longitude) {
        this.longitude = longitude;
    }

    public static GPSLocation createRandomGPSLocation(){
        double latitude = Math.round((Math.random() * (0.08) + 52.3000)* 10000.0) / 10000.0;
        double longitude = Math.round((Math.random() * (0.2) + 4.8) * 10000.0) / 10000.0;
        return new GPSLocation(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GPSLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
