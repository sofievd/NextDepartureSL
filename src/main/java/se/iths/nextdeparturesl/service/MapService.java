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


    //TODO: adding logging
    // TODO: adding exit for when a file does not exist or mapping fails
    //TODO: remove maps that are not used.
    public MapService() {
        //adding the making of the maps
        System.out.println("starting making maps");
        // stops = fileUtil.createStopMapWithStopId();
        StopIdTostopTimes = fileUtil.createStopTimeMapWithStopId();
        TripIdTotrips = fileUtil.createTripMapWithTripId();
        routes = fileUtil.createRouteMapWithRouteId();
        //calendars = fileUtil.createCalenderMapWithServiceId();
        calendarDates = fileUtil.createCalendarDateMapWithServiceId();
        stopNameToStopId = fileUtil.createStopIdMapWithStopName();
        serviceIdToTripId = fileUtil.createTripIdListMapWithServiceId();
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
