package se.iths.nextdeparturesl.model;

/**
 * StopTime represents stop times for a trip in a GTFS feed.
 * Contains timing and sequencing information for stops along a trip.
 *
 * @author Sofie Van Dingenen
 */
public class StopTime {


    private String tripId;
    private String arrivalTime;
    private String departureTime;
    private String stopId;
    private int stopSequence;
    private String stopHeadsign;
    private int pickupType;
    private int dropOffType;
    private double shapeDistTraveled;
    private int timepoint;

    public StopTime(String tripId,
                    String arrivalTime,
                    String departureTime,
                    String stopId,
                    int stopSequence,
                    String stopHeadsign,
                    int pickupType,
                    int dropOffType,
                    double shapeDistTraveled,
                    int timepoint) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this.stopHeadsign = stopHeadsign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled = shapeDistTraveled;
        this.timepoint = timepoint;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getStopId() {
        return stopId;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public int getPickupType() {
        return pickupType;
    }

    public void setPickupType(int pickupType) {
        this.pickupType = pickupType;
    }

    public int getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(int dropOffType) {
        this.dropOffType = dropOffType;
    }

    public double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(double shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }

    public int getTimePoint() {
        return timepoint;
    }

    public void setTimePoint(int timePoint) {
        this.timepoint = timePoint;
    }
}
