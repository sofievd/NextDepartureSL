package se.iths.nextdeparturesl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.util.GtfsFileHandler;

import java.io.File;
import java.util.*;

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
    private final String path;

    private final GtfsFileHandler gtfsFileHandler;
    private static final String STOP_FILE_NAME = "stops.txt";
    private static final String STOP_TIMES_FILE_NAME = "stop_times.txt";
    private static final String TRIP_FILE_NAME = "trips.txt";
    private static final String ROUTE_FILE_NAME = "routes.txt";
    private static final String CALENDAR_DATE_FILE_NAME = "calendar_dates.txt";

    public GtfsDataHolder(String path) {
        this.path = path;
        File file = new File(path+"sl.zip");
        this.gtfsFileHandler = new GtfsFileHandler(file);
    }

    /**
     * Creates all the necessary maps in memory
     */
    public void createMaps() {
        log.info("Starting making maps");
        //stationList = gtfsFileHandler.getStopNameList(path + STOP_FILE_NAME);
       // stopIdToStopTimes = createStopTimeMapWithStopId();
//        tripIdToTrips = createTripMapWithTripId();
//        routeIdToRoutes = createRouteMapWithRouteId();
//        serviceIdToCalendarDates = createCalendarDateMapWithServiceId();
//        stopNameToStopId = createStopIdMapWithStopName();
//        serviceIdToTripId = createTripIdListMapWithServiceId();
        log.info("Finished making maps");
    }

    /**
     * Creates a map that groups {@link StopTime} entries by their stop ID.
     *
     * @return a map where each key is a stop ID and the value is a list of StopTime entries for that stop
     */
    public Map<String, List<StopTime>> createStopTimeMapWithStopId() {
        log.info("creating StopTime map with StopId");
        Map<String, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = gtfsFileHandler.getStopTimeList();
        stopTimeList.forEach(stopTime -> {
            String stopId = stopTime.getStopId();
            map.putIfAbsent(stopId, new ArrayList<>());
            map.get(stopId).add(stopTime);
        });
//        for (StopTime stopTime : stopTimeList) {
//            String stopId = stopTime.getStopId();
//            map.putIfAbsent(stopId, new ArrayList<>());
//            map.get(stopId).add(stopTime);
//        }
        return map;
    }

    /**
     * Creates a map with {@link Trip} entries and their trip ID.
     *
     * @return a map where each key is a trip ID and the value is the corresponding trip
     */
    public Map<String, Trip> createTripMapWithTripId() {
        log.info("creating Trip map with TripId");
        Map<String, Trip> map = new HashMap<>();
        List<Trip> tripList = gtfsFileHandler.getTripList(); //getTripList();
        for (Trip trip : tripList) {
            String tripId = trip.getTripId();
            map.put(tripId, trip);
        }
        return map;
    }

    /**
     * Creates a map with {@link Route} entries and their route ID.
     *
     * @return a map where each key is a route ID and the value is the corresponding route
     */
    public Map<String, Route> createRouteMapWithRouteId() {
        log.info("creating Route map with RouteId");
        Map<String, Route> map = new HashMap<>();
        List<Route> routeList = gtfsFileHandler.getRouteList();
        for (Route route : routeList) {
            String routeId = route.getRouteId();
            map.put(routeId, route);
        }
        return map;
    }

    /**
     * Creates a map that groups {@link CalendarDate} entries by their service ID.
     *
     * @return a map where each key is a service ID and the value is a list of calendarDate entries for that service
     */
    public Map<String, List<CalendarDate>> createCalendarDateMapWithServiceId() {
        log.info("creating CalendarDate map with ServiceId");
        Map<String, List<CalendarDate>> map = new HashMap<>();
        List<CalendarDate> calendarDateList = gtfsFileHandler.getCalendarDateList();
        for (CalendarDate calendar : calendarDateList) {
            String calendarDateId = calendar.getServiceId();
            map.putIfAbsent(calendarDateId, new ArrayList<>());
            map.get(calendarDateId).add(calendar);
        }

        return map;
    }

    /**
     * Crates a map that groups stop ID's by their stop Name.
     */
    public Map<String, List<String>> createStopIdMapWithStopName() {
        log.info("creating StopId map with StopName");
       // String stopsFilePath = path + STOP_FILE_NAME;
        List<Stop> stopList = gtfsFileHandler.getStopList();
        Map<String, List<String>> result = new HashMap<>();
        for (Stop stop : stopList) {
            if (stop.getLocationType() != 0) {
                continue;
            }
            result.putIfAbsent(stop.getStopName(), new ArrayList<>());
            result.get(stop.getStopName()).add(stop.getStopId());
        }
        return result;
    }

    public Map<String, List<String>> createTripIdListMapWithServiceId() {
        log.info("creating TripIdList map with ServiceId");
        Map<String, List<String>> map = new HashMap<>();
        List<Trip> tripList = gtfsFileHandler.getTripList(); //getTripList();
        List<String> serviceIdList = getServiceIDListFromTripList(tripList);
        for (String serviceId : serviceIdList) {
            if (!map.containsKey(serviceId)) {
                List<String> tripIdList = getTripListWithServiceId(serviceId, tripList).stream().toList();
                map.put(serviceId, tripIdList);
            }
        }
        return map;
    }


    public List<String> getServiceIDListFromTripList(List<Trip> tripList) {
        log.info("creating list of service Ids from a list of trips");
        List<String> serviceIdList = new ArrayList<>();
        for (Trip trip : tripList) {
            String serviceId = trip.getServiceId();
            if (!serviceIdList.contains(serviceId)) {
                serviceIdList.add(serviceId);
            }
        }
        return serviceIdList;
    }

    public Set<String> getTripListWithServiceId(String serviceId, List<Trip> tripList) {
        Set<String> resultList = new HashSet<>();
        for (Trip trip : tripList) {
            if (trip.getServiceId().equals(serviceId)) {
                resultList.add(trip.getTripId());
            }
        }
        return resultList;
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
}
