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

    public String getRouteId() {
        return route_id;
    }

    public void setRouteId(String route_id) {
        this.route_id = route_id;
    }

    public String getAgencyId() {
        return agency_id;
    }

    public void setAgencyId(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getRouteShortName() {
        return route_short_name;
    }

    public void setRouteShortName(String route_short_name) {
        this.route_short_name = route_short_name;
    }

    public String getRouteLongName() {
        return route_long_name;
    }

    public void setRouteLongName(String route_long_name) {
        this.route_long_name = route_long_name;
    }

    public String getRouteType() {
        return route_type;
    }

    public void setRouteType(String route_type) {
        this.route_type = route_type;
    }

    public String getRouteDesc() {
        return route_desc;
    }

    public void setRouteDesc(String route_desc) {
        this.route_desc = route_desc;
    }

}
