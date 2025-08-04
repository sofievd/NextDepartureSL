package se.iths.nextdeparturesl.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.VehicleTypeConverter;
import se.iths.nextdeparturesl.view.Departure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DepartureFinder {

    private static final Logger log = LogManager.getLogger();
    private static final String GTFS_BOARDING_TYPE_NO_BOARDING = "1";
    private static final int MAX_RESULTS = 20;
    public static final int MAX_DAYS_FORWARD = 3;

    private GtfsDataHolder gtfsDataHolder = new GtfsDataHolder("src/main/resources/static/");
    private final VehicleTypeConverter vehicleTypeConverter = new VehicleTypeConverter();

    private Map<String, List<StopTime>> stopIdToStopTimes;
    private Map<String, Trip> tripIdToTrips;
    private Map<String, Route> routeIdToRoutes;
    private Map<String, List<CalendarDate>> serviceIdToCalendarDates;
    private Map<String, List<String>> stopNameToStopId;
    private Map<String, List<String>> serviceIdToTripId;
    private List<String> stationList;


    public DepartureFinder() {
    }

    public DepartureFinder(GtfsDataHolder gtfsDataHolder) {
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

    public Map<String, List<StopTime>> createTripIdToStopTimeMap(List<StopTime> stopTimeslist) {
        log.info("making stop times map from StopTimes List");
        Map<String, List<StopTime>> stopTimesMap = new HashMap<>();
        for (StopTime stopTime : stopTimeslist) {
            stopTimesMap.putIfAbsent(stopTime.getTripId(), new ArrayList<>());
            stopTimesMap.get(stopTime.getTripId()).add(stopTime);
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

    private void createDeparture(StopTime stopTime, Route route, List<Departure> departures, LocalDate date) {
        log.info("Creating a departure from stopTime {}, route {} and date {}", stopTime, route, date);
        String destination = stopTime.getStopHeadsign();
        String departureTime = formatOffsetTime(stopTime.getDepartureTime(), date);
        String arrivalTime = formatOffsetTime(stopTime.getArrivalTime(), date);
        String vehicleType = vehicleTypeConverter.convert(route.getType());
        String vehicleTypeCode = route.getType();
        String routeLongName = route.getLongName();
        String routeDescription = route.getDesc();
        String lineNumber = route.getShortName();

        Departure departure = new Departure(destination, departureTime, arrivalTime, vehicleType, vehicleTypeCode, routeLongName, routeDescription, lineNumber);
        departures.add(departure);
    }

    private String formatOffsetTime(int offsetTime, LocalDate date) {
       // int offsetTimeSeconds = offsetTimeToSeconds(offsetTime);
        LocalDateTime dateTime = date.atTime(LocalTime.of(0,0,0)).plusSeconds(offsetTime);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"));
    }

    private int offsetTimeToSeconds(String offsetTime) {
        int hour = Integer.parseInt(offsetTime.substring(0, 2));
        int minute = Integer.parseInt(offsetTime.substring(3, 5));
        int second = Integer.parseInt(offsetTime.substring(6, 8));
        return (3600*hour)+(60*minute)+second;
    }

    public List<Departure> getDeparturesWithStopTime(List<StopTime> stopTimes, LocalDate date) {
        log.info("getting departures with stopTime {} and date {}", stopTimes, date);
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = tripIdToTrips.get(stopTime.getTripId());
            Route route = routeIdToRoutes.get(trip.getRouteId());

            createDeparture(stopTime, route, departures, date);
        }
        return departures;
    }

    public List<Departure> getDeparturesFromStopName(String stopName, LocalDateTime searchDateTime) {
        log.info("getting departures from stopName {} and dateTime {}", stopName, searchDateTime);

        if (stopNameToStopId == null) {
            log.warn("stopNameToStopId is null, no departures found");
            return new ArrayList<>();
        }

        Set<String> StationIdList = getStationIdWithName(stopName);
        if (StationIdList.isEmpty()) {
            log.warn("no stationIdList is null/ empty, no departures found");
            return new ArrayList<>();
        }

        List<StopTime> stopTimesList = getStopTimesFromStationId(StationIdList);
        stopTimesList.removeIf(stopTime -> String.valueOf(stopTime.getPickupType()).equals(GTFS_BOARDING_TYPE_NO_BOARDING));

        Map<String, List<StopTime>> stopTimeMap = createTripIdToStopTimeMap(stopTimesList);
        List<Trip> tripsAtStop = getTripsFromFromStopTimes(stopTimesList);
        List<String> serviceIdsForTripsAtStop = getServiceIdFromTrips(tripsAtStop);

        LocalDate searchDate = searchDateTime.toLocalDate();
        List<Departure> departuresList = getDeparturesAtDate(tripsAtStop,serviceIdsForTripsAtStop,stopTimeMap, searchDate);
        removeDeparturesBeforeSearchDateTime(searchDateTime, departuresList);

        for (int daysOffset = 1; departuresList.size() < MAX_RESULTS && daysOffset < MAX_DAYS_FORWARD; daysOffset++) {
            LocalDate dayAfter = LocalDateTime.now().plusDays(daysOffset).toLocalDate();
            List<Departure> departuresTomorrow = getDeparturesAtDate(tripsAtStop, serviceIdsForTripsAtStop, stopTimeMap, dayAfter);
            departuresList.addAll(departuresTomorrow);
        }

        departuresList.sort(Comparator.comparing(Departure::getDepartureTime));
        int limit = Math.min(departuresList.size(), MAX_RESULTS);
        return departuresList.subList(0, limit);
    }

    private void removeDeparturesBeforeSearchDateTime(LocalDateTime searchDateTime, List<Departure> departuresList) {
        String earliestTimeString = searchDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"));
        departuresList.removeIf(departure -> departure.getDepartureTime().compareTo(earliestTimeString) < 0);
    }


    private List<Departure> getDeparturesAtDate(List<Trip> tripsAtStop, List<String> serviceIdsForTripsAtStop,
                                               Map<String, List<StopTime>> stopTimeMap, LocalDate date) {
        log.info("getting departures at {}", date);


        List<String> serviceIdListTomorrow = getServiceIdsActiveAtDate(serviceIdsForTripsAtStop, date);

        List<Trip> tripsFromServiceTomorrow = getTripsForServiceIds(serviceIdListTomorrow);
        tripsFromServiceTomorrow.retainAll(tripsAtStop);

        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripsFromServiceTomorrow, stopTimeMap);
        List<StopTime> stopTimeList = stopTimesFromService.stream().sorted(Comparator.comparing(StopTime::getDepartureTime)).toList();

        return getDeparturesWithStopTime(stopTimeList, date);
    }

    private List<Trip> getTripsForServiceIds(List<String> serviceIds) {
        log.info("getting trips {} for serviceIds", serviceIds.size());
        Set<Trip> tripFromServiceSet = new HashSet<>();
        for (String serviceId : serviceIds) {
            tripFromServiceSet.addAll(getTripsWithServiceId(serviceId));
        }
        List<Trip> tripFromService = new ArrayList<>(tripFromServiceSet);
        tripFromService.sort(Comparator.comparing(Trip::getTripId));
        return tripFromService;
    }

    private List<String> getServiceIdsActiveAtDate(List<String> serviceIdList, LocalDate date) {
        log.info("getting serviceIds active at {}, filtering from {} ids", date, serviceIdList.size());
        Set<String> serviceIdSet = new HashSet<>();
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        for (String serviceId : serviceIdList) {
            if (isServiceIdActiveAtDate(serviceId, dateStr)) {
                serviceIdSet.add(serviceId);
            }
        }
        List<String> activeIds = new ArrayList<>(serviceIdSet);
        activeIds.sort(Comparator.naturalOrder());

        log.info("Filtered {} serviceIds for date {}, retained {} ids", serviceIdList.size(), date, activeIds.size());
        return activeIds;
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
