package se.iths.nextdeparturesl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.util.GtfsFileHandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<BigInteger, List<StopTime>> StopIdTostopTimes;
    private Map<BigInteger, Trip> TripIdTotrips;
    private Map<String, Route> routes;
    private Map<BigInteger, List<CalendarDate>> calendarDates;
    private Map<String, List<BigInteger>> stopNameToStopId;
    private Map<BigInteger, List<BigInteger>> serviceIdToTripId;
    private List<String> stationList;
    private String path;

    private final GtfsFileHandler gtfsFileHandler = new GtfsFileHandler();
    private final String STOP_FILE_PATH = "stops.txt";
    private final String STOP_TIMES_FILE_PATH = "stop_times.txt";
    private final String TRIP_FILE_PATH = "trips.txt";
    private final String ROUTE_FILE_PATH = "routes.txt";
    private final String CALENDAR_FILE_PATH = "calendar.txt";
    private final String CALENDAR_DATE_FILE_PATH = "calendar_dates.txt";

    public GtfsDataHolder(String path) {
        this.path = path;
    }

    /**
     * Creates all the necessary maps in memory
     */
    public void createMaps() {
        log.info("Starting making maps");
        stationList = gtfsFileHandler.getStopNameList(path+STOP_FILE_PATH);
        StopIdTostopTimes = createStopTimeMapWithStopId(path+STOP_TIMES_FILE_PATH);
        TripIdTotrips = createTripMapWithTripId(path+TRIP_FILE_PATH);
        routes = createRouteMapWithRouteId(path+ROUTE_FILE_PATH);
        calendarDates = createCalendarDateMapWithServiceId(path+CALENDAR_DATE_FILE_PATH);
        stopNameToStopId = createStopIdMapWithStopName(path+STOP_FILE_PATH);
        serviceIdToTripId = createTripIdListMapWithServiceId(path+TRIP_FILE_PATH);
        log.info("Finished making maps");
    }

    /**
     * Creates a map that groups {@link StopTime} entries by their stop ID.
     * @param path The filepath to the CSV-file containing stoptime records
     * @return a map where each key is a stop ID and the value is a list of StopTime entries for that stop
     */
    public Map<BigInteger, List<StopTime>> createStopTimeMapWithStopId(String path) {
        log.info("creating StopTime map with StopId");
        Map<BigInteger, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = gtfsFileHandler.parseCsvToStopTime(path);
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

    /**
     * Creates a map with {@link Trip} entries and their trip ID.
     * @param path The filepath to the CSV-file containing trip records
     * @return a map where each key is a trip ID and the value is the corresponding trip
     */
    public Map<BigInteger, Trip> createTripMapWithTripId(String path) {
        log.info("creating Trip map with TripId");
        Map<BigInteger, Trip> map = new HashMap<>();
        List<Trip> tripList = gtfsFileHandler.parseCsvToTrip(path); //getTripList();
        for (Trip trip : tripList) {
            BigInteger tripId = new BigInteger(trip.getTrip_id());
            map.put(tripId, trip);
        }
        return map;
    }

    /**
     * Creates a map with {@link Route} entries and their route ID.
     * @param path The filepath to the CSV-file containing route records
     * @return a map where each key is a route ID and the value is the corresponding route
     */
    public Map<String, Route> createRouteMapWithRouteId(String path) {
        log.info("creating Route map with RouteId");
        Map<String, Route> map = new HashMap<>();
        List<Route> routeList = gtfsFileHandler.parseCsvToRoute(path);
        for (Route route : routeList) {
            String routeId = route.getRoute_id();
            map.put(routeId, route);
        }
        return map;
    }

    /**
     * Creates a map that groups {@link CalendarDate} entries by their service ID.
     * @param path the filepath to the CSV-file containing calendarDates records.
     * @return a map where each key is a service ID and the value is a list of calendarDate entries for that service
     */
    public Map<BigInteger, List<CalendarDate>> createCalendarDateMapWithServiceId(String path) {
        log.info("creating CalendarDate map with ServiceId");
        Map<BigInteger, List<CalendarDate>> map = new HashMap<>();
        List<CalendarDate> calendarDateList = gtfsFileHandler.parseCsvToCalendarDate(path);
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

    /**
     * Crates a map that groups stop ID's by their stop Name
     * @param path
     * @return
     */
    public Map<String, List<BigInteger>> createStopIdMapWithStopName(String path) {
        log.info("creating StopId map with StopName");
        Map<String, List<BigInteger>> map = new HashMap<>();
        List<String> nameList = gtfsFileHandler.getStopNameList(path);
        List<Stop> stopList = gtfsFileHandler.parseCsvToStop(path);
        for (String name : nameList) {
            List<BigInteger> stopIdList = gtfsFileHandler.getStopIdListWithStopName(name, stopList);
            map.put(name, stopIdList);
        }
        return map;
    }

    public Map<BigInteger, List<BigInteger>> createTripIdListMapWithServiceId(String path) {
        log.info("creating TripIdList map with ServiceId");
        Map<BigInteger, List<BigInteger>> map = new HashMap<>();
        List<Trip> tripList = gtfsFileHandler.parseCsvToTrip(path); //getTripList();
        List<BigInteger> serviceIdList = gtfsFileHandler.getServiceIDListFromTripList(tripList);
        for (BigInteger serviceId : serviceIdList) {
            if (!map.containsKey(serviceId)) {
                List<BigInteger> tripIdList = gtfsFileHandler.getTripListWithServiceId(serviceId, tripList).stream().toList();
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
