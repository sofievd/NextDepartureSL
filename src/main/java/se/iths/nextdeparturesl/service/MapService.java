package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.util.FileUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapService {
    //private Map<BigInteger, Stop> stops;
    private Map<BigInteger, List<StopTime>> StopIdTostopTimes;
    private Map<BigInteger, Trip> TripIdTotrips;
    private Map<String, Route> routes;
    private Map<BigInteger, CalendarGtfs> calendars;
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
    public MapService() {
    }

    public void createMaps() {
        //adding the making of the maps
        System.out.println("starting making maps");
        stationList = fileUtil.getStopNameList(STOP_FILE_PATH);
        // stops = fileUtil.createStopMapWithStopId();
        StopIdTostopTimes = createStopTimeMapWithStopId(STOP_TIMES_FILE_PATH);
        TripIdTotrips = createTripMapWithTripId(TRIP_FILE_PATH);
        routes = createRouteMapWithRouteId(ROUTE_FILE_PATH);
        calendars = fileUtil.createCalenderMapWithServiceId(CALENDAR_FILE_PATH);
        calendarDates = createCalendarDateMapWithServiceId(CALENDAR_DATE_FILE_PATH);
        stopNameToStopId = createStopIdMapWithStopName(STOP_FILE_PATH);
        serviceIdToTripId = createTripIdListMapWithServiceId(TRIP_FILE_PATH);
        //tripIdToStopTimes = fileUtil.createStopTimeMapWithTripId();
        System.out.println("finshed making maps");
    }

    public void createTestMaps(){
        StopIdTostopTimes = createStopTimeMapWithStopId("src/test/resources/GTFS_SL_TEST/stop-times.txt");
        TripIdTotrips = createTripMapWithTripId("src/test/resources/GTFS_SL_TEST/trips.txt");
        routes = createRouteMapWithRouteId("src/test/resources/GTFS_SL_TEST/routes.txt");
        calendarDates = createCalendarDateMapWithServiceId("src/test/resources/GTFS_SL_TEST/calendar_dates.txt");
        stopNameToStopId = createStopIdMapWithStopName("src/test/resources/GTFS_SL_TEST/stops.txt");
        serviceIdToTripId = createTripIdListMapWithServiceId("src/test/resources/GTFS_SL_TEST/trips.txt");
    }

    public Map<BigInteger, List<StopTime>> createStopTimeMapWithStopId(String path) {
        Map<BigInteger, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = fileUtil.parseCsvToStopTime(path);
        for (StopTime stopTime : stopTimeList) {
            BigInteger stopId = new BigInteger(stopTime.getStop_id());
            if (map.containsKey(stopId)) {
                map.get(stopId).add(stopTime);
            } else {
                List<StopTime> stopTimes = new ArrayList<>();
                stopTimes.add(stopTime);
                map.put(stopId, stopTimes);
            }
        }
        return map;
    }
    public Map<BigInteger, Trip> createTripMapWithTripId(String path) {
        Map<BigInteger, Trip> map = new HashMap<>();
        List<Trip> tripList = fileUtil.parseCsvToTrip(path); //getTripList();
        for (Trip trip : tripList) {
            BigInteger tripId = new BigInteger(trip.getTrip_id());
            map.put(tripId, trip);
        }
        return map;
    }

    public Map<String, Route> createRouteMapWithRouteId(String path) {
        Map<String, Route> map = new HashMap<>();
        List<Route> routeList = fileUtil.parseCsvToRoute(path);
        for (Route route : routeList) {
            String routeId = route.getRoute_id();
            map.put(routeId, route);
        }
        return map;
    }

    public Map<BigInteger, List<CalendarDate>> createCalendarDateMapWithServiceId(String path) {
        Map<BigInteger, List<CalendarDate>> map = new HashMap<>();
        List<CalendarDate> calendarDateList = fileUtil.parseCsvToCalendarDate(path);
        for (CalendarDate calendar : calendarDateList) {
            BigInteger calendarDateId = new BigInteger(calendar.getService_id());
            if (map.containsKey(calendarDateId)) {
                map.get(calendarDateId).add(calendar);
            } else {
                List<CalendarDate> calendarDates = new ArrayList<>();
                calendarDates.add(calendar);
                map.put(calendarDateId, calendarDates);
            }
        }

        return map;
    }

    public Map<String, List<BigInteger>> createStopIdMapWithStopName(String path) {
        Map<String, List<BigInteger>> map = new HashMap<>();
        List<String> nameList = fileUtil.getStopNameList(path);
        for (String name : nameList) {
            List<BigInteger> stopIdList = fileUtil.getStopIdListWithStopName(name, path);
            map.put(name, stopIdList);
        }
        return map;
    }

    public Map<BigInteger, List<BigInteger>> createTripIdListMapWithServiceId(String path) {
        Map<BigInteger, List<BigInteger>> map = new HashMap<>();
        List<Trip> tripList = fileUtil.parseCsvToTrip(path); //getTripList();
        List<BigInteger> serviceIdList = fileUtil.getServiceIDListFromTripList(tripList);
        for (BigInteger serviceId : serviceIdList) {
            if (!map.containsKey(serviceId)) {
                List<BigInteger> tripIdList = fileUtil.getTripListWithServiceId(serviceId, tripList).stream().toList();
                map.put(serviceId, tripIdList);
            }
        }
        return map;
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
