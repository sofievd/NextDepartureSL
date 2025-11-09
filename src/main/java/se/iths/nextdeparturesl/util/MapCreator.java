package se.iths.nextdeparturesl.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.dto.Station;
import se.iths.nextdeparturesl.model.*;

import java.util.*;

public class MapCreator {
    private static final Logger log = LogManager.getLogger();

    private GtfsFileHandler gtfsFileHandler;
    private static final String GTFS_LOCATION_TYPE_STATION = "1";


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
        List<Trip> tripList = gtfsFileHandler.getTripList();
        tripList.forEach(trip -> {
            String tripId = trip.getTripId();
            map.put(tripId, trip);
        });
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
        routeList.forEach(route -> {
            String routeId = route.getRouteId();
            map.put(routeId, route);
        });
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
        calendarDateList.forEach(calendarDate -> {
            String calendarDateId = calendarDate.getServiceId();
            map.putIfAbsent(calendarDateId, new ArrayList<>());
            map.get(calendarDateId).add(calendarDate);
        });
        return map;
    }

    /**
     * Crates a map that groups stop ID's by their stop Name.
     */
    public Map<String, List<String>> createStopIdMapWithStopName() {
        log.info("creating StopId map with StopName");
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

    public Map<String, String> createStopNameWithStopId() {
        log.info("creating StopName map with StopId");
        Map<String, String> result = new HashMap<>();
        List<Stop> stopList = gtfsFileHandler.getStopList();
        for (Stop stop : stopList) {
            if (stop.getLocationType() != 0) {
                continue;
            }
            result.putIfAbsent(stop.getStopId(), stop.getStopName());
        }
        return result;
    }

    public Map<String, List<String>> createTripIdListMapWithServiceId() {
        log.info("creating TripIdList map with ServiceId");
        Map<String, List<String>> map = new HashMap<>();
        List<Trip> tripList = gtfsFileHandler.getTripList();
        List<String> serviceIdList = getServiceIDListWithTripList(tripList);
        for (String serviceId : serviceIdList) {
            if (!map.containsKey(serviceId)) {
                List<String> tripIdList = getTripListWithServiceId(serviceId, tripList).stream().toList();
                map.put(serviceId, tripIdList);
            }
        }
        return map;
    }

    public Map<String, List<String>> createParentStationIdToStops() {
        log.info("creating ParentStationIdToStops");
        Map<String, List<String>> map = new HashMap<>();
        List<Station> parentIdList = getStopNameList();
        for (Station parentId : parentIdList) {
            List<String> stopIdList = getStopIdWithParentId(parentId.getId());
            map.put(parentId.getId(), stopIdList);
        }
        return map;
    }

    public Map<String, List<StopTime>> createTripIdToStopTimes() {
        Map<String, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = gtfsFileHandler.getStopTimeList();
        stopTimeList.forEach(stopTime -> {
            String tripId = stopTime.getTripId();
            map.putIfAbsent(tripId, new ArrayList<>());
            map.get(tripId).add(stopTime);
        });
        return map;
    }


    private List<String> getServiceIDListWithTripList(List<Trip> tripList) {
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

    private Set<String> getTripListWithServiceId(String serviceId, List<Trip> tripList) {
        Set<String> resultList = new HashSet<>();
        for (Trip trip : tripList) {
            if (trip.getServiceId().equals(serviceId)) {
                resultList.add(trip.getTripId());
            }
        }
        return resultList;
    }

    private List<String> getStopIdWithParentId(String stopId) {
        List<String> stopIdList = new ArrayList<>();
        for (Stop stop : gtfsFileHandler.getStopList()) {
            if(stop.getParentStation() != null && stop.getParentStation().equals(stopId)){
                stopIdList.add(stop.getStopId());
            }
        }
        return stopIdList;
    }

    public List<Station> getStopNameList() {
        log.info("creating list of stop names");
        List<Station> stationList = new ArrayList<>();
        for (Stop stop : gtfsFileHandler.getStopList()) {
            if (String.valueOf(stop.getLocationType()).equals(GTFS_LOCATION_TYPE_STATION)) {
                Station station = new Station(stop.getStopId(), stop.getStopName());
                stationList.add(station);
            }
        }
        return stationList;
    }

    public void setFileHandler(GtfsFileHandler fileHandler) {
        gtfsFileHandler = fileHandler;
    }
}
