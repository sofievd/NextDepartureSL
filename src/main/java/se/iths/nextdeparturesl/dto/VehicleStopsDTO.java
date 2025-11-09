package se.iths.nextdeparturesl.dto;

import java.util.List;

public class VehicleStopsDTO {
    private String tripId;
    private String lineNumber;
    private String destination;
    private String routeName;
    private String routeDescription;
    private List<StopDTO> nextStops;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public List<StopDTO> getNextStops() {
        return nextStops;
    }

    public void setNextStops(List<StopDTO> nextStops) {
        this.nextStops = nextStops;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
}
