package se.iths.nextdeparturesl.model;

/**
 * Stop Represents a transit stop in a transportation system.
 * This class is used for mapping data from a CSV file using OpenCSV annotations.
 *
 * @author Sofie Van Dingenen
 */
public class Stop {

    private String id;
    private String name;
    private double lat;
    private double lon;
    private int locationType;
    private String parentStation;
    private String platformCode;

    public Stop(String id,
                String name,
                double lat,
                double lon,
                int locationType,
                String parentStation,
                String platformCode) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.locationType = locationType;
        this.parentStation = parentStation;
        this.platformCode = platformCode;
    }

    public String getStopId() {
        return id;
    }

    public void setStopId(String id) {
        this.id = id;
    }

    public String getStopName() {
        return name;
    }

    public void setStopName(String name) {
        this.name = name;
    }

    public double getStopLat() {
        return lat;
    }

    public void setStopLat(double lat) {
        this.lat = lat;
    }

    public double getStopLon() {
        return lon;
    }

    public void setStopLon(double lon) {
        this.lon = lon;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}
