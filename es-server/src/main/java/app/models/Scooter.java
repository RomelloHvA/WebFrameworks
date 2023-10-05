package app.models;

public class Scooter {
    private long id;
    private String tag;
    private String status;
    private GPSLocation gpsLocation;
    private int mileage;
    private int batteryCharge;

    public enum Status {
        IDLE, IN_USE, MAINTENANCE, UNAVAILABLE
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
        scooter.setStatus("IN_USE");
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStatus(String status) {
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

    public String getStatus() {
        return status;
    }

    public GPSLocation getLocation() {
        return gpsLocation;
    }

    public int getMileage() {
        return mileage;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public int getId() {
        return (int) id;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                ", location='" + gpsLocation + '\'' +
                ", mileage=" + mileage +
                ", batteryCharge=" + batteryCharge +
                '}';
    }


}
