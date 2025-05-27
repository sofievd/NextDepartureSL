package se.iths.nextdeparturesl.model;

import com.opencsv.bean.CsvBindByName;

/**
 * CalendarDate represents a single calendar exception date in a GTFS feed.
 * Used to add or remove service for specific dates.
 *
 * @author Sofie Van Dingenen
 */
public class CalendarDate {
    @CsvBindByName
    private String service_id;
    @CsvBindByName
    private String date;
    @CsvBindByName
    private String exception_type;

    public String getServiceId() {
        return service_id;
    }

    public void setServiceId(String service_id) {
        this.service_id = service_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExceptionType() {
        return exception_type;
    }

    public void setExceptionType(String exception_type) {
        this.exception_type = exception_type;
    }
}
