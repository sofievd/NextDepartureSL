package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

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

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }


}
