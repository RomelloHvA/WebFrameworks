package app.models;

public class GPSLocation {
    private double latitude;
    private double longitude;

    public GPSLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
        double latitude = Math.round((Math.random() * (0.08) + 52.3000)* 10000) / 10000;
        double longitude = Math.round((Math.random() * (0.2) + 4.8) * 10000) / 10000;
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
