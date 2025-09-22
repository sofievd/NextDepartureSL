package se.iths.nextdeparturesl.model;

/**
 * CalendarDate represents a single calendar exception date in a GTFS feed.
 * Used to add or remove service for specific dates.
 *
 * @author Sofie Van Dingenen
 */
public class CalendarDate {

    private String serviceId;

    private String date;

    private int exceptionType;

    public CalendarDate(String serviceId, String date, int exceptionType) {
        this.serviceId = serviceId;
        this.date = date;
        this.exceptionType = exceptionType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(int exceptionType) {
        this.exceptionType = exceptionType;
    }
}
