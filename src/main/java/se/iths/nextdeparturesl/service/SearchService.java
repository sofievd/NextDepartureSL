package se.iths.nextdeparturesl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.VehicleTypeConverter;
import se.iths.nextdeparturesl.view.Departure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SearchService {

    private static final Logger log = LogManager.getLogger();

    private GtfsDataHolder gtfsDataHolder = new GtfsDataHolder("src/main/resources/static/GTFS_SL/");
    private final VehicleTypeConverter vehicleTypeConverter = new VehicleTypeConverter();

    private Map<String, List<StopTime>> stopIdToStopTimes;
    private Map<String, Trip> tripIdToTrips;
    private Map<String, Route> routeIdToRoutes;
    private Map<String, List<CalendarDate>> serviceIdToCalendarDates;
    private Map<String, List<String>> stopNameToStopId;
    private Map<String, List<String>> serviceIdToTripId;
    private List<String> stationList;


    public SearchService() {
    }

    public SearchService(GtfsDataHolder gtfsDataHolder) {
        this.gtfsDataHolder = gtfsDataHolder;
    }

    public void setUp() {
        log.info("Setting up search service");
        gtfsDataHolder.createMaps();
        stopIdToStopTimes = gtfsDataHolder.getStopIdToStopTimes();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();
        serviceIdToCalendarDates = gtfsDataHolder.getServiceIdToCalendarDates();
        stopNameToStopId = gtfsDataHolder.getStopNameToStopId();
        serviceIdToTripId = gtfsDataHolder.getServiceIdToTripId();
        stationList = gtfsDataHolder.getStationList();
    }

    public List<String> getStationList() {
        log.info("getting stations list");
        List<String> stations = new ArrayList<>();
        if (stationList == null) {
            return stations;
        } else {
            return stationList;
        }

    }

    public Set<String> getStationIdWithName(String name) {
        log.debug("getting stations with name {}", name);
        Set<String> stationIds = new HashSet<>();
        if (stopNameToStopId.containsKey(name)) {
            stationIds.addAll(stopNameToStopId.get(name));
        } else {
            log.debug("no stations found with name {}", name);
            return stationIds;
        }
        return stationIds;
    }

    public Set<StopTime> getStopTimesWithStationId(String stopId) {
        log.debug("getting stop times with id {}", stopId);
        Set<StopTime> stopTimes = new HashSet<>();
        if (stopIdToStopTimes.containsKey(stopId)) {
            stopTimes.addAll(stopIdToStopTimes.get(stopId));
        } else {
            log.warn("no stop times found with id {}", stopId);
            return stopTimes;
        }
        return stopTimes;
    }

    public Map<String, List<StopTime>> makeMap(List<StopTime> stopTimeslist) {
        log.info("making stop times map from StopTimes List");
        Map<String, List<StopTime>> stopTimesMap = new HashMap<>();
        for (StopTime stopTime : stopTimeslist) {
            if (stopTimesMap.containsKey(stopTime.getTripId())) {
                stopTimesMap.get(stopTime.getTripId()).add(stopTime);
            } else {
                List<StopTime> stopTimes = new ArrayList<>();
                stopTimes.add(stopTime);
                stopTimesMap.put(stopTime.getTripId(), stopTimes);
            }
        }
        return stopTimesMap;
    }

    public Set<Trip> getTripWithTripId(String tripId) {
        log.debug("getting trip with id {}", tripId);
        Set<Trip> tripIdList = new HashSet<>();
        if (tripIdToTrips.containsKey(tripId)) {
            tripIdList.add(tripIdToTrips.get(tripId));
        } else {
            log.warn("no trip found with id {}", tripId);
            return tripIdList;
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripIdList;
    }

    public Set<String> getServiceIdWithTripId(String tripId) {
        log.debug("getting service id with trip id {}", tripId);
        Set<String> calendarId = new HashSet<>();
        Trip trip = tripIdToTrips.get(tripId);
        if (trip != null) {
            calendarId.add(trip.getServiceId());
        } else {
            log.warn("no trip found with id {}", tripId);
            return calendarId;
        }
        return calendarId;
    }

    public boolean isServiceIdActiveAtDate(String serviceID, String date) {
        log.debug("getting today service id with service id {} and date {}", serviceID, date);
        if (!serviceIdToCalendarDates.containsKey(serviceID)) {
            log.warn("no service found with id {} for date {}", serviceID, date);
            return false;
        }
        List<CalendarDate> calendarDateList = serviceIdToCalendarDates.get(serviceID);
        for (CalendarDate calendarDate : calendarDateList) {
            if (calendarDate.getDate().equals(date)) {
               return true;
            }
        }
        return false;
    }

    public Set<String> getTomorrowServiceIdWithServiceId(String serviceID) {
        log.debug("getting tomorrow service id with service id {}", serviceID);
        Set<String> calendarDateId = new HashSet<>();
        String tomorrow = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (serviceIdToCalendarDates.containsKey(serviceID)) {
            List<CalendarDate> calendarDateList = serviceIdToCalendarDates.get(serviceID);
            for (CalendarDate calendarDate : calendarDateList) {
                if (calendarDate.getDate().equals(tomorrow)) {
                    calendarDateId.add(calendarDate.getServiceId());
                }
            }
        } else {
            log.warn("no service found with id {} for date {}", serviceID, tomorrow);
        }
        return calendarDateId;
    }

    public Set<Trip> getTripsWithServiceId(String serviceId) {
        log.debug("getting trips with service id {}", serviceId);
        Set<Trip> tripList = new HashSet<>();
        List<String> tripIds = new ArrayList<>();
        if (serviceIdToTripId.containsKey(serviceId)) {
            tripIds.addAll(serviceIdToTripId.get(serviceId));
        }
        for (String tripId : tripIds) {
            if (tripIdToTrips.containsKey(tripId)) {
                tripList.add(tripIdToTrips.get(tripId));
            }
        }
        return tripList;
    }

    public Set<StopTime> getStopTimeWithTrip(List<Trip> trips, Map<String, List<StopTime>> map) {
        log.debug("getting stop times with trip {}", trips);
        Set<StopTime> stopTimeList = new HashSet<>();
        for (Trip trip : trips) {
            if (map.containsKey(trip.getTripId())) {
                stopTimeList.addAll(map.get(trip.getTripId()));
            }
        }
        return stopTimeList;
    }

    public List<Departure> getDeparturesWithStopTimeToday(String timeNow, List<StopTime> stopTimes, String date) {
        log.info("getting departures with stopTime for day {} and time {}", date, timeNow);
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = tripIdToTrips.get(stopTime.getTripId());
            Route route = routeIdToRoutes.get(trip.getRouteId());
            String parseTime = stopTime.getDepartureTime();

            if (parseTime.compareTo(timeNow) > 0) {
                createDeparture(stopTime, route, departures, date);
            }

        }
        return departures;
    }

    private void createDeparture(StopTime stopTime, Route route, List<Departure> departures, String date) {
        log.info("Creating a departure from stopTime {}, route {} and date {}", stopTime, route, date);
        String destination = stopTime.getStopHeadsign();
        String departureTime = date + "-" + stopTime.getDepartureTime();
        String arrivalTime = date + "-" + stopTime.getArrivalTime();
        String vehicleType = vehicleTypeConverter.convert(route.getRouteType());
        String vehicleTypeCode = route.getRouteType();
        String routeLongName = route.getRouteLongName();
        String routeDescription = route.getRouteDesc();
        String lineNumber = route.getRouteShortName();

        Departure departure = new Departure(destination, departureTime, arrivalTime, vehicleType, vehicleTypeCode, routeLongName, routeDescription, lineNumber);
        departures.add(departure);
    }

    public List<Departure> getDeparturesWithStopTimeTomorow(List<StopTime> stopTimes, String date) {
        log.info("getting departures with stopTime {} and date {}", stopTimes, date);
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = tripIdToTrips.get(stopTime.getTripId());
            Route route = routeIdToRoutes.get(trip.getRouteId());

            createDeparture(stopTime, route, departures, date);
        }
        return departures;
    }

    public List<Departure> getDeparturesFromStopName(String stopName, String dateTime) {
        log.info("getting departures from stopName {} and dateTime {}", stopName, dateTime);
        String date = dateTime.substring(0, 8);
        String time = dateTime.substring(9);

        List<Departure> departuresList = new ArrayList<>();
        List<StopTime> stopTimesList;
        List<Trip> tripList;
        List<String> serviceIdList;
        List<String> serviceIdListNow;
        List<String> serviceIdListTomorow;

        if (stopNameToStopId == null) {
            log.warn("stopNameToStopId is null, no departures found");
            return departuresList;
        }
        Set<String> StationIdList = getStationIdWithName(stopName);

        if (StationIdList.isEmpty()) {
            log.warn("no stationIdList is null/ empty, no departures found");
            return departuresList;
        }

        stopTimesList = getStopTimesFromStationId(StationIdList);
        List<StopTime> tempList = new ArrayList<>();
        for (StopTime stopTime : stopTimesList) {
            if (stopTime.getStopHeadsign().equalsIgnoreCase(stopName) ||
                    stopTime.getStopHeadsign().equalsIgnoreCase(stopName + " station")) {
                tempList.add(stopTime);
            }
        }
        stopTimesList.removeAll(tempList);

        Map<String, List<StopTime>> stopTimeMap = makeMap(stopTimesList);
        tripList = getTripsFromFromStopTimes(stopTimesList);
        serviceIdList = getServiceIdFromTrips(tripList);

        if ("16:00:00".compareTo(time) < 0) {
            serviceIdListNow = getServiceIdForTodayFromServiceIds(serviceIdList, date);
            serviceIdListTomorow = getServiceIdForTomorrowFromServiceIds(serviceIdList);
            departuresList = getDeparturesTomorow(tripList, serviceIdListTomorow, stopTimeMap);

        } else {
            serviceIdListNow = getServiceIdForTodayFromServiceIds(serviceIdList, date);
        }
        List<Trip> tripsFromService = getTripsFromTodaysServiceIds(serviceIdListNow);
        tripList.retainAll(tripsFromService);
        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripList, stopTimeMap);
        List<StopTime> stopTimeList = stopTimesFromService.stream().sorted(Comparator.comparing(StopTime::getDepartureTime)).toList();
        departuresList.addAll(getDeparturesWithStopTimeToday(time, stopTimeList, date));
        departuresList.sort(Comparator.comparing(Departure::getDepartureTime));
        int limit = Math.min(departuresList.size(), 20);
        return departuresList.subList(0, limit);
    }

    public List<Departure> getDeparturesTomorow(List<Trip> tripList, List<String> serviceIdList, Map<String, List<StopTime>> stopTimeMap) {
        log.info("getting departures tomorow");
        List<Trip> tripsFromServiceTomorrow = getTripsFromTodaysServiceIds(serviceIdList);
        tripList.retainAll(tripsFromServiceTomorrow);
        List<Departure> departuresList;
        String tomorrow = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripList, stopTimeMap);
        List<StopTime> stopTimeList = stopTimesFromService.stream().sorted(Comparator.comparing(StopTime::getDepartureTime)).toList();
        departuresList = getDeparturesWithStopTimeTomorow(stopTimeList, tomorrow);
        return departuresList;
    }

    private List<Trip> getTripsFromTodaysServiceIds(List<String> serviceIdListToday) {
        log.info("getting trips from todays with serviceIdList");
        Set<Trip> tripFromServiceSet = new HashSet<>();
        for (String serviceId : serviceIdListToday) {
            tripFromServiceSet.addAll(getTripsWithServiceId(serviceId));
        }
        List<Trip> tripFromService = new ArrayList<>(tripFromServiceSet);
        tripFromService.sort(Comparator.comparing(Trip::getTripId));
        return tripFromService;
    }

    private List<String> getServiceIdForTodayFromServiceIds(List<String> serviceIdList, String date) {
        log.info("getting serviceId from todays with serviceIdList");
        Set<String> serviceIdSet = new HashSet<>();
        for (String serviceId : serviceIdList) {
            if (isServiceIdActiveAtDate(serviceId, date)){
                serviceIdSet.add(serviceId);
            }
        }
        List<String> serviceIdListToday = new ArrayList<>(serviceIdSet);
        serviceIdListToday.sort(Comparator.naturalOrder());
        return serviceIdListToday;
    }

    private List<String> getServiceIdForTomorrowFromServiceIds(List<String> serviceIdList) {
        log.info("getting serviceId from tomorrow with serviceIdList");
        Set<String> serviceIdSet = new HashSet<>();
        for (String serviceId : serviceIdList) {
            serviceIdSet.addAll(getTomorrowServiceIdWithServiceId(serviceId));
        }
        List<String> serviceIdListTomorrow = new ArrayList<>(serviceIdSet);
        serviceIdListTomorrow.sort(Comparator.naturalOrder());
        return serviceIdListTomorrow;
    }

    private List<String> getServiceIdFromTrips(List<Trip> tripList) {
        log.info("getting serviceId from trips");
        Set<String> serviceIdSet = new HashSet<>();

        for (Trip tripId : tripList) {
            serviceIdSet.addAll(getServiceIdWithTripId(tripId.getTripId()));
        }
        List<String> serviceIdList = new ArrayList<>(serviceIdSet);
        serviceIdList.sort(Comparator.naturalOrder());
        return serviceIdList;
    }

    private List<Trip> getTripsFromFromStopTimes(List<StopTime> stopTimesList) {
        log.info("getting trips from stop times");
        Set<Trip> tripSet = new HashSet<>();
        for (StopTime stopTime : stopTimesList) {
            tripSet.addAll(getTripWithTripId(stopTime.getTripId()));
        }
        List<Trip> tripList = new ArrayList<>(tripSet);
        tripList.sort(Comparator.comparing(Trip::getTripId));
        return tripList;
    }

    private List<StopTime> getStopTimesFromStationId(Set<String> StationIdList) {
        log.info("getting stop times from stationId");
        Set<StopTime> stopTimeSet = new HashSet<>();
        for (String stationId : StationIdList) {
            stopTimeSet.addAll(getStopTimesWithStationId(stationId));
        }
        List<StopTime> stopTimes = new ArrayList<>(stopTimeSet);
        stopTimes.sort(Comparator.comparing(StopTime::getDepartureTime));
        return stopTimes;
    }
}
