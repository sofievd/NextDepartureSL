package se.iths.nextdeparturesl.view;

public class Departure {
    private final String destination;
    private final String departureTime;
    private final String arrivalTime;
    private final String vehicleType;
    private final String vehicleTypeCode;
    private final String routeName;
    private final String routeDescription;
    private final String lineNumber;

    public Departure(String destination, String departureTime,String arrivalTime,
                     String vehicleType,String vehicleTypeCode, String routeName,
                     String routeDescription, String lineNumber) {
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.vehicleType = vehicleType;
        this.vehicleTypeCode = vehicleTypeCode;
        this.routeName = routeName;
        this.routeDescription = routeDescription;
        this.lineNumber = lineNumber;
    }
    public String getDestination() {
        return destination;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getVehicleTypeCode() {
        return vehicleTypeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleTypeCode='" + vehicleTypeCode + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeDescription='" + routeDescription + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                '}';
    }

}
