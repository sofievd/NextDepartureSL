package se.iths.nextdeparturesl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;

import java.util.List;
import java.util.Map;

/**
 * MapService Provides access to in-memory mappings of GTFS data for efficient lookups and operations.
 * This service acts as a data structure layer for connecting GTFS components like stops, trips, routes, and calendars.
 *
 * @author Sofie Van Dingenen
 */
public class GtfsDataHolder {
    private static final Logger log = LogManager.getLogger();
    private Map<String, List<StopTime>> stopIdToStopTimes;
    private Map<String, Trip> tripIdToTrips;
    private Map<String, Route> routeIdToRoutes;
    private Map<String, List<CalendarDate>> serviceIdToCalendarDates;
    private Map<String, List<String>> stopNameToStopId;
    private Map<String, List<String>> serviceIdToTripId;
    private List<String> stationList;

    private static GtfsDataHolder gtfsDataHolder;

    private GtfsDataHolder() {
    }

    public static GtfsDataHolder getInstance() {
        if (gtfsDataHolder == null) {
            gtfsDataHolder = new GtfsDataHolder();
        }
        return gtfsDataHolder;
    }

    public List<String> getStationList() {
        return stationList;
    }

    public Map<String, List<StopTime>> getStopIdToStopTimes() {
        return stopIdToStopTimes;
    }

    public Map<String, List<String>> getStopNameToStopId() {
        return stopNameToStopId;
    }

    public Map<String, List<String>> getServiceIdToTripId() {
        return serviceIdToTripId;
    }

    public Map<String, List<CalendarDate>> getServiceIdToCalendarDates() {
        return serviceIdToCalendarDates;
    }

    public Map<String, Route> getRouteIdToRoutes() {
        return routeIdToRoutes;
    }

    public Map<String, Trip> getTripIdToTrips() {
        return tripIdToTrips;
    }

    public void setStopIdToStopTimes(Map<String, List<StopTime>> stopIdToStopTimes) {
        this.stopIdToStopTimes = stopIdToStopTimes;
    }

    public void setTripIdToTrips(Map<String, Trip> tripIdToTrips) {
        this.tripIdToTrips = tripIdToTrips;
    }

    public void setRouteIdToRoutes(Map<String, Route> routeIdToRoutes) {
        this.routeIdToRoutes = routeIdToRoutes;
    }

    public void setServiceIdToCalendarDates(Map<String, List<CalendarDate>> serviceIdToCalendarDates) {
        this.serviceIdToCalendarDates = serviceIdToCalendarDates;
    }

    public void setStopNameToStopId(Map<String, List<String>> stopNameToStopId) {
        this.stopNameToStopId = stopNameToStopId;
    }

    public void setServiceIdToTripId(Map<String, List<String>> serviceIdToTripId) {
        this.serviceIdToTripId = serviceIdToTripId;
    }

    public void setStationList(List<String> stationList) {
        this.stationList = stationList;
    }

}
