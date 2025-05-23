package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Route represents a transit route in a GTFS feed.
 * Includes identifiers, names, and descriptive details.
 *
 * @author Sofie Van Dingenen
 */
public class Route {
    @CsvBindByName
    private String route_id;
    @CsvBindByName
    private String agency_id;
    @CsvBindByName
    private String route_short_name;
    @CsvBindByName
    private String route_long_name;
    @CsvBindByName
    private String route_type;
    @CsvBindByName
    private String route_desc;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getRoute_short_name() {
        return route_short_name;
    }

    public void setRoute_short_name(String route_short_name) {
        this.route_short_name = route_short_name;
    }

    public String getRoute_long_name() {
        return route_long_name;
    }

    public void setRoute_long_name(String route_long_name) {
        this.route_long_name = route_long_name;
    }

    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

    public String getRoute_desc() {
        return route_desc;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }

}
