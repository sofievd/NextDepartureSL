package se.iths.nextdeparturesl.view;

public class Departure {
    private String destination;
    private String departureTime;
    private String vehicleType;
    private String lineNumber;
    public Departure(String destination, String departureTime, String vehicleType, String lineNumber) {
        this.destination = destination;
        this.departureTime = departureTime;
        this.vehicleType = vehicleType;
        this.lineNumber = lineNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                '}';
    }
}
