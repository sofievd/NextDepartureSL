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

    public void setRouteId(String route_id) {
        this.routeId = route_id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String service_id) {
        this.serviceId = service_id;
    }

    public String getTripId() {
        return id;
    }

    public void setTripId(String trip_id) {
        this.id = trip_id;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String trip_headsign) {
        this.tripHeadsign = trip_headsign;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String direction_id) {
        this.directionId = direction_id;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shape_id) {
        this.shapeId = shape_id;
    }
}
