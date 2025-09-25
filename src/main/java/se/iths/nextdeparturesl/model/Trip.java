package se.iths.nextdeparturesl.model;

/**
 * Trip represents a specific trip (instance of a route) in a GTFS feed.
 * Ties together a route and service schedule with unique trip identifiers.
 */
public class Trip {
    private String id;
    private String routeId;
    private String serviceId;
    private String tripHeadsign;
    private String directionId;
    private String shapeId;

    public Trip(String id,
                String routeId,
                String serviceId,
                String tripHeadsign,
                String directionId,
                String shapeId) {
        this.id = id;
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.tripHeadsign = tripHeadsign;
        this.directionId = directionId;
        this.shapeId = shapeId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTripId() {
        return id;
    }

    public void setTripId(String tripId) {
        this.id = tripId;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }
}
