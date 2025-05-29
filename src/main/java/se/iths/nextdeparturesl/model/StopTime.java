package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

/**
 * StopTime represents stop times for a trip in a GTFS feed.
 * Contains timing and sequencing information for stops along a trip.
 *
 * @author Sofie Van Dingenen
 */
public class StopTime {

    @CsvBindByName
    private String trip_id;
    @CsvBindByName
    private String arrival_time;
    @CsvBindByName
    private String departure_time;
    @CsvBindByName
    private String stop_id;
    @CsvBindByName
    private String stop_sequence;
    @CsvBindByName
    private String stop_headsign;
    @CsvBindByName
    private String pickup_type;
    @CsvBindByName
    private String drop_off_type;
    @CsvBindByName
    private String shape_dist_traveled;
    @CsvBindByName
    private String timepoint;

    public String getTripId() {
        return trip_id;
    }

    public void setTripId(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getArrivalTime() {
        return arrival_time;
    }

    public void setArrivalTime(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getStopId() {
        return stop_id;
    }

    public void setStopId(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStopSequence() {
        return stop_sequence;
    }

    public void setStopSequence(String stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public String getStopHeadsign() {
        return stop_headsign;
    }

    public void setStopHeadsign(String stop_headsign) {
        this.stop_headsign = stop_headsign;
    }

    public String getPickupType() {
        return pickup_type;
    }

    public void setPickupType(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    public String getDropOffType() {
        return drop_off_type;
    }

    public void setDropOffType(String drop_off_type) {
        this.drop_off_type = drop_off_type;
    }

    public String getShapeDistTraveled() {
        return shape_dist_traveled;
    }

    public void setShapeDistTraveled(String shape_dist_traveled) {
        this.shape_dist_traveled = shape_dist_traveled;
    }

    public String getTimePoint() {
        return timepoint;
    }

    public void setTimePoint(String timePoint) {
        this.timepoint = timePoint;
    }
}
