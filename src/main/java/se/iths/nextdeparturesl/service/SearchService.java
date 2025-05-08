package se.iths.nextdeparturesl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.VehicleTypeConverter;
import se.iths.nextdeparturesl.view.Departure;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//TODO: adding logging

public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private MapService mapService = new MapService();
    private final VehicleTypeConverter vehicleTypeConverter = new VehicleTypeConverter();

    private Map<BigInteger, List<StopTime>> stopIdTostopTimes; //= mapService.getStopIdTostopTimes();
    private Map<BigInteger, Trip> TripIdTotrips;// = mapService.getTripIdTotrips();
    private Map<String, Route> routes;// = mapService.getRoutes();
    private Map<BigInteger, List<CalendarDate>> calendarDates;//= mapService.getCalendarDates();
    private Map<String, List<BigInteger>> stopNameToStopId;//= mapService.getStopNameToStopId();
    private Map<BigInteger, List<BigInteger>> serviceIdToTripId; //= mapService.getServiceIdToTripId();
    private List<String> stationList; //= mapService.getStationList();


    public SearchService() {
    }

    public SearchService(MapService mapService) {
        this.mapService = mapService;
    }

    public void setUp() {
        log.info("Setting up search service");
        mapService.createMaps();
        stopIdTostopTimes = mapService.getStopIdTostopTimes();
        TripIdTotrips = mapService.getTripIdTotrips();
        routes = mapService.getRoutes();
        calendarDates = mapService.getCalendarDates();
        stopNameToStopId = mapService.getStopNameToStopId();
        serviceIdToTripId = mapService.getServiceIdToTripId();
        stationList = mapService.getStationList();
    }

    public void testSetUp() {
        log.trace("Setting up search service");
        mapService.createTestMaps();
        stopIdTostopTimes = mapService.getStopIdTostopTimes();
        TripIdTotrips = mapService.getTripIdTotrips();
        routes = mapService.getRoutes();
        calendarDates = mapService.getCalendarDates();
        stopNameToStopId = mapService.getStopNameToStopId();
        serviceIdToTripId = mapService.getServiceIdToTripId();
        stationList = mapService.getStationList();
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

    public Set<BigInteger> getStationIdWithName(String name) {
        log.debug("getting stations with name {}", name);
        Set<BigInteger> stationIds = new HashSet<>();
        if (stopNameToStopId.containsKey(name)) {
            stationIds.addAll(stopNameToStopId.get(name));
        }else{
            log.debug("no stations found with name {}", name);
            return stationIds;
        }
        return stationIds;
    }

    public Set<StopTime> getStopTimesWithStationId(BigInteger stopId) {
        log.debug("getting stop times with id {}", stopId);
        Set<StopTime> stopTimes = new HashSet<>();
        if (stopIdTostopTimes.containsKey(stopId)) {
            stopTimes.addAll(stopIdTostopTimes.get(stopId));
        }else {
            log.debug("no stop times found with id {}", stopId);
            return stopTimes;
        }
        return stopTimes;
    }

    public Map<String, List<StopTime>> makeMap(List<StopTime> stopTimeslist) {
        log.debug("making stop times map from StopTimes List");
        Map<String, List<StopTime>> stopTimesMap = new HashMap<>();
        for (StopTime stopTime : stopTimeslist) {
            if (stopTimesMap.containsKey(stopTime.getTrip_id())) {
                stopTimesMap.get(stopTime.getTrip_id()).add(stopTime);
            } else {
                List<StopTime> stopTimes = new ArrayList<>();
                stopTimes.add(stopTime);
                stopTimesMap.put(stopTime.getTrip_id(), stopTimes);
            }
        }
        return stopTimesMap;
    }

    public Set<Trip> getTripWithTripId(BigInteger tripId) {
        log.debug("getting trip with id {}", tripId);
        Set<Trip> tripIdList = new HashSet<>();
        if (TripIdTotrips.containsKey(tripId)) {
            tripIdList.add(TripIdTotrips.get(tripId));
        }
        else{
            log.debug("no trip found with id {}", tripId);
            return tripIdList;
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripIdList;
    }

    public Set<BigInteger> getServiceIdWithTripId(BigInteger tripId) {
        log.debug("getting service id with trip id {}", tripId);
        Set<BigInteger> calendarId = new HashSet<>();
        Trip trip = TripIdTotrips.get(tripId);
        if (TripIdTotrips.containsKey(tripId)) {
            calendarId.add(new BigInteger(trip.getService_id()));
        }
        else{
            log.debug("no trip found with id {}", tripId);
            return calendarId;
        }
        return calendarId;
    }

    public Set<BigInteger> getTodayServiceIdWithServiceId(BigInteger serviceID, String date) {
        log.debug("getting today service id with service id {} and date {}", serviceID, date);
        Set<BigInteger> calendarDateId = new HashSet<>();
        if (calendarDates.containsKey(serviceID)) {
            List<CalendarDate> calendarDateList = calendarDates.get(serviceID);
            for (CalendarDate calendarDate : calendarDateList) {
                if (calendarDate.getDate().equals(date)) {
                    calendarDateId.add(new BigInteger(calendarDate.getService_id()));
                }
            }
        }
        else {
            log.debug("no service found with id {} for date {}", serviceID, date);
            return calendarDateId;
        }
        return calendarDateId;
    }

    public Set<BigInteger> getTomorrowServiceIdWithServiceId(BigInteger serviceID) {
        log.debug("getting tomorrow service id with service id {}", serviceID);
        Set<BigInteger> calendarDateId = new HashSet<>();
        String tomorrow = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (calendarDates.containsKey(serviceID)) {
            List<CalendarDate> calendarDateList = calendarDates.get(serviceID);
            for (CalendarDate calendarDate : calendarDateList) {
                if (calendarDate.getDate().equals(tomorrow)) {
                    calendarDateId.add(new BigInteger(calendarDate.getService_id()));
                }
            }
        }
        else{
            log.debug("no service found with id {} for date {}", serviceID, tomorrow);
        }
        return calendarDateId;
    }

    public Set<Trip> getTripsWithServiceId(BigInteger serviceId) {
        log.debug("getting trips with service id {}", serviceId);
        Set<Trip> tripList = new HashSet<>();
        List<BigInteger> tripIds = new ArrayList<>();
        if (serviceIdToTripId.containsKey(serviceId)) {
            tripIds.addAll(serviceIdToTripId.get(serviceId));
        }
        for (BigInteger tripId : tripIds) {
            if (TripIdTotrips.containsKey(tripId)) {
                tripList.add(TripIdTotrips.get(tripId));
            }
        }
        return tripList;
    }

    public Set<StopTime> getStopTimeWithTrip(List<Trip> trips, Map<String, List<StopTime>> map) {
        log.debug("getting stop times with trip {}", trips);
        Set<StopTime> stopTimeList = new HashSet<>();
        for (Trip trip : trips) {
            if (map.containsKey(trip.getTrip_id())) {
                stopTimeList.addAll(map.get(trip.getTrip_id()));
            }
        }
        return stopTimeList;
    }
    
    public List<Departure> getDeparturesWithStopTimeToday(String timeNow, List<StopTime> stopTimes, String date) {
        log.info("getting departures with stopTime for day {} and time {}", date, timeNow);
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = TripIdTotrips.get(new BigInteger(stopTime.getTrip_id()));
            Route route = routes.get(trip.getRoute_id());
            String parseTime = stopTime.getDeparture_time();

            if (parseTime.compareTo(timeNow) > 0) {
                createDeparture(stopTime, route, departures, date);
            }

        }
        return departures;
    }

    private void createDeparture(StopTime stopTime, Route route, List<Departure> departures, String date) {
        log.debug("Creating a departure from stopTime {}, route {} and date {}", stopTime, route, date);
        String destination = stopTime.getStop_headsign();
        String departureTime = date + "-" +  stopTime.getDeparture_time();
        String arrivalTime = date + "-" + stopTime.getArrival_time();
        String vehicleType = vehicleTypeConverter.convert(route.getRoute_type());
        String vehicleTypeCode = route.getRoute_type();
        String routeLongName = route.getRoute_long_name();
        String routeDescription = route.getRoute_desc();
        String lineNumber = route.getRoute_short_name();

        Departure departure = new Departure(destination, departureTime, arrivalTime, vehicleType, vehicleTypeCode,routeLongName, routeDescription, lineNumber);
        departures.add(departure);
    }

    public List<Departure> getDeparturesWithStopTimeTomorow(List<StopTime> stopTimes, String date) {
        log.debug("getting departures with stopTime {} and date {}", stopTimes, date);
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = TripIdTotrips.get(new BigInteger(stopTime.getTrip_id()));
            Route route = routes.get(trip.getRoute_id());

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
        List<BigInteger> serviceIdList;
        List<BigInteger> serviceIdListNow;
        List<BigInteger> serviceIdListTomorow;

        if (stopNameToStopId == null) {
            log.warn("stopNameToStopId is null, no departures found");
            return departuresList;
        }
        Set<BigInteger> StationIdList = getStationIdWithName(stopName);
        //List<BigInteger> stationIdList= StationIdList.stream().sorted().toList();

        if (StationIdList.isEmpty()) {
            log.warn("no stationIdList is null/ empty, no departures found");
            return departuresList;
        }

        stopTimesList = getStopTimesFromStationId(StationIdList);

        Map<String, List<StopTime>> stopTimeMap = makeMap(stopTimesList);
        tripList = getTripsFromFromStopTimes(stopTimesList);
        serviceIdList = getServiceIdFromTrips(tripList);

        if ("23:00:00".compareTo(time) < 0) {
            serviceIdListNow = getServiceIdForTodayFromServiceIds(serviceIdList, date);
            serviceIdListTomorow = getServiceIdForTommorowFromServiceIds(serviceIdList);
            departuresList = getDeparturesTomorow(tripList, serviceIdListTomorow, stopTimeMap);

        } else {
            serviceIdListNow = getServiceIdForTodayFromServiceIds(serviceIdList, date);
        }

        List<Trip> tripsFromService = getTripsFromTodaysServiceIds(serviceIdListNow);
        tripList.retainAll(tripsFromService);

        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripList, stopTimeMap);
        List<StopTime> stopTimeList = stopTimesFromService.stream().sorted(Comparator.comparing(StopTime::getDeparture_time)).toList();

        departuresList.addAll(getDeparturesWithStopTimeToday(time, stopTimeList, date));

        return departuresList;
    }

    public List<Departure> getDeparturesTomorow(List<Trip> tripList, List<BigInteger> serviceIdList, Map<String, List<StopTime>> stopTimeMap) {
        log.debug("getting departures tomorow") ;
        List<Trip> tripsFromServiceTomorrow = getTripsFromTodaysServiceIds(serviceIdList);
        tripList.retainAll(tripsFromServiceTomorrow);
        List<Departure> departuresList;
        String tomorrow = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripList, stopTimeMap);
        List<StopTime> stopTimeList = stopTimesFromService.stream().sorted(Comparator.comparing(StopTime::getDeparture_time)).toList();
        departuresList = getDeparturesWithStopTimeTomorow(stopTimeList, tomorrow);
        return departuresList;
    }

    private List<Trip> getTripsFromTodaysServiceIds(List<BigInteger> serviceIdListToday) {
        log.debug("getting trips from todays with serviceIdList");
        List<Trip> tripFromService = new ArrayList<>();
        for (BigInteger serviceId : serviceIdListToday) {
            tripFromService.addAll(getTripsWithServiceId(serviceId));
        }
        tripFromService.sort(Comparator.comparing(Trip::getTrip_id));
        return tripFromService;
    }

    private List<BigInteger> getServiceIdForTodayFromServiceIds(List<BigInteger> serviceIdList, String date) {
        log.debug("getting serviceId from todays with serviceIdList");
        List<BigInteger> serviceIdListToday = new ArrayList<>();
        for (BigInteger serviceId : serviceIdList) {
            serviceIdListToday.addAll(getTodayServiceIdWithServiceId(serviceId, date));
        }
        serviceIdListToday.sort(Comparator.comparing(BigInteger::intValue));
        return serviceIdListToday;
    }

    private List<BigInteger> getServiceIdForTommorowFromServiceIds(List<BigInteger> serviceIdList) {
        log.debug("getting serviceId from tomorrow with serviceIdList");
        List<BigInteger> serviceIdListTommorow = new ArrayList<>();
        for (BigInteger serviceId : serviceIdList) {
            serviceIdListTommorow.addAll(getTomorrowServiceIdWithServiceId(serviceId));
        }
        serviceIdListTommorow.sort(Comparator.comparing(BigInteger::intValue));
        return serviceIdListTommorow;
    }

    private List<BigInteger> getServiceIdFromTrips(List<Trip> tripList) {
        log.debug("getting serviceId from trips");
        List<BigInteger> serviceIdList = new ArrayList<>();
        for (Trip tripId : tripList) {
            serviceIdList.addAll(getServiceIdWithTripId(new BigInteger(tripId.getTrip_id())));
        }
        serviceIdList.sort(Comparator.comparing(BigInteger::intValue));
        return serviceIdList;
    }

    private List<Trip> getTripsFromFromStopTimes(List<StopTime> stopTimesList) {
        log.debug("getting trips from stop times");
        List<Trip> tripList = new ArrayList<>();
        for (StopTime stopTime : stopTimesList) {
            tripList.addAll(getTripWithTripId(new BigInteger(stopTime.getTrip_id())));
        }
        tripList.sort(Comparator.comparing(Trip::getTrip_id));
        return tripList;
    }

    private List<StopTime> getStopTimesFromStationId(Set<BigInteger> StationIdList) {
        log.debug("getting stop times from stationId");
        List<StopTime> stopTimes = new ArrayList<>();
        for (BigInteger stationId : StationIdList) {
            stopTimes.addAll(getStopTimesWithStationId(stationId));
        }
        stopTimes.sort(Comparator.comparing(StopTime::getDeparture_time));
        return stopTimes;
    }
}
