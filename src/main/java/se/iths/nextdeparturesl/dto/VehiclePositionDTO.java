package se.iths.nextdeparturesl.dto;

public class VehiclePositionDTO {
    private String tripId;
    private double latitude;
    private double longitude;
    private double bearing;
    private double speed;
    private int type;
    private String lineNumber;
    private long timestamp;

    public VehiclePositionDTO(String id, double latitude, double longitude, double bearing, double speed) {
        this.tripId = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
        this.speed = speed;
    }

    public String getId() {
        return tripId;
    }

    public void setId(String id) {
        this.tripId = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VehiclePositionDTO{" +
                "tripId='" + tripId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", bearing=" + bearing +
                ", speed=" + speed +
                ", type=" + type +
                ", lineNumber='" + lineNumber + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
