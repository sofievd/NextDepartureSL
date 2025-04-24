package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

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


    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(String stop_lon) {
        this.stop_lon = stop_lon;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public String getParent_station() {
        return parent_station;
    }

    public void setParent_station(String parent_station) {
        this.parent_station = parent_station;
    }

    public String getPlatform_code() {
        return platform_code;
    }

    public void setPlatform_code(String platform_code) {
        this.platform_code = platform_code;
    }
}
