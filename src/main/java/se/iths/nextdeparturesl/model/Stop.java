package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Stop Represents a transit stop in a transportation system.
 * This class is used for mapping data from a CSV file using OpenCSV annotations.
 *
 * @author Sofie Van Dingenen
 */
public class Stop {
    @CsvBindByName
    private String stop_id;
    @CsvBindByName
    private String stop_name;
    @CsvBindByName
    private String stop_lat;
    @CsvBindByName
    private String stop_lon;
    @CsvBindByName
    private String location_type;
    @CsvBindByName
    private String parent_station;
    @CsvBindByName
    private String platform_code;


    public String getStopId() {
        return stop_id;
    }

    public void setStopId(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStopName() {
        return stop_name;
    }

    public void setStopName(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStopLat() {
        return stop_lat;
    }

    public void setStopLat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStopLon() {
        return stop_lon;
    }

    public void setStopLon(String stop_lon) {
        this.stop_lon = stop_lon;
    }

    public String getLocationType() {
        return location_type;
    }

    public void setLocationType(String location_type) {
        this.location_type = location_type;
    }

    public String getParentStation() {
        return parent_station;
    }

    public void setParentStation(String parent_station) {
        this.parent_station = parent_station;
    }

    public String getPlatformCode() {
        return platform_code;
    }

    public void setPlatformCode(String platform_code) {
        this.platform_code = platform_code;
    }
}
