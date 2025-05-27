package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Trip represents a specific trip (instance of a route) in a GTFS feed.
 * Ties together a route and service schedule with unique trip identifiers.
 */
public class Trip {
    @CsvBindByName
    private String route_id;
    @CsvBindByName
    private String service_id;
    @CsvBindByName
    private String trip_id;
    @CsvBindByName
    private String trip_headsign;
    @CsvBindByName
    private String direction_id;
    @CsvBindByName
    private String shape_id;

    public String getRouteId() {
        return route_id;
    }

    public void setRouteId(String route_id) {
        this.route_id = route_id;
    }

    public String getServiceId() {
        return service_id;
    }

    public void setServiceId(String service_id) {
        this.service_id = service_id;
    }

    public String getTripId() {
        return trip_id;
    }

    public void setTripId(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTripHeadsign() {
        return trip_headsign;
    }

    public void setTripHeadsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    public String getDirectionId() {
        return direction_id;
    }

    public void setDirectionId(String direction_id) {
        this.direction_id = direction_id;
    }

    public String getShapeId() {
        return shape_id;
    }

    public void setShapeId(String shape_id) {
        this.shape_id = shape_id;
    }
}
