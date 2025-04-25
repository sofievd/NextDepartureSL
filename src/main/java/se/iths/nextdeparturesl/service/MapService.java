package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.util.FileUtil;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MapService {
    //private Map<BigInteger, Stop> stops;
    private Map<BigInteger, List<StopTime>> StopIdTostopTimes;
    private Map<BigInteger, Trip> TripIdTotrips;
    private Map<String, Route> routes;
    // private Map<BigInteger, CalendarGtfs> calendars;
    private Map<BigInteger, List<CalendarDate>> calendarDates;
    private Map<String, List<BigInteger>> stopNameToStopId;
    private Map<BigInteger, List<BigInteger>> serviceIdToTripId;
    //  private Map<BigInteger, List<StopTime>> tripIdToStopTimes;
    private List<String> stationList;
    private final FileUtil fileUtil = new FileUtil();
    private final String STOP_FILE_PATH = "src/main/resources/static/GTFS_SL/stops.txt";
    private final String STOP_TIMES_FILE_PATH = "src/main/resources/static/GTFS_SL/stop_times.txt";
    private final String TRIP_FILE_PATH = "src/main/resources/static/GTFS_SL/trips.txt";
    private final String ROUTE_FILE_PATH = "src/main/resources/static/GTFS_SL/routes.txt";
    private final String CALENDAR_FILE_PATH = "src/main/resources/static/GTFS_SL/calendar.txt";
    private final String CALENDAR_DATE_FILE_PATH = "src/main/resources/static/GTFS_SL/calendar_dates.txt";



    //TODO: adding logging
    // TODO: adding exit for when a file does not exist or mapping fails
    //TODO: remove maps that are not used.
    public MapService() {
        //adding the making of the maps
        System.out.println("starting making maps");
        // stops = fileUtil.createStopMapWithStopId();
        StopIdTostopTimes = fileUtil.createStopTimeMapWithStopId(STOP_TIMES_FILE_PATH);
        TripIdTotrips = fileUtil.createTripMapWithTripId(TRIP_FILE_PATH);
        routes = fileUtil.createRouteMapWithRouteId(ROUTE_FILE_PATH);
       // calendars = fileUtil.createCalenderMapWithServiceId(CALENDAR_FILE_PATH);
        calendarDates = fileUtil.createCalendarDateMapWithServiceId(CALENDAR_DATE_FILE_PATH);
        stopNameToStopId = fileUtil.createStopIdMapWithStopName(STOP_FILE_PATH);
        serviceIdToTripId = fileUtil.createTripIdListMapWithServiceId(TRIP_FILE_PATH);
        stationList = fileUtil.getStationList();
        //tripIdToStopTimes = fileUtil.createStopTimeMapWithTripId();

        System.out.println("finshed making maps");
    }


    public List<String> getStationList() {
        return stationList;
    }

    public Map<BigInteger, List<StopTime>> getStopIdTostopTimes() {
        return StopIdTostopTimes;
    }

    public Map<String, List<BigInteger>> getStopNameToStopId() {
        return stopNameToStopId;
    }

    public Map<BigInteger, List<BigInteger>> getServiceIdToTripId() {
        return serviceIdToTripId;
    }

    public Map<BigInteger, List<CalendarDate>> getCalendarDates() {
        return calendarDates;
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public Map<BigInteger, Trip> getTripIdTotrips() {
        return TripIdTotrips;
    }
}
