package se.iths.nextdeparturesl.view;

public class Departure {
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String vehicleType;
    private String routeName;
    private String routeDescription;
    private String lineNumber;

    public Departure(String destination, String departureTime,String arrivalTime, String vehicleType, String routeName, String routeDescription,
            String lineNumber) {
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.vehicleType = vehicleType;
        this.routeName = routeName;
        this.routeDescription = routeDescription;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                '}';
    }
}
