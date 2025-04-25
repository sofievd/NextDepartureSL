package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.view.Departure;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// TODO: adding logging
//TODO: error handling
//TODO: refactoring

public class SearchService {

    private MapService mapService = new MapService();

    private Map<BigInteger, List<StopTime>> stopIdTostopTimes; //= mapService.getStopIdTostopTimes();
    private Map<BigInteger, Trip> TripIdTotrips;// = mapService.getTripIdTotrips();
    private Map<String, Route> routes;// = mapService.getRoutes();
    private Map<BigInteger, List<CalendarDate>> calendarDates;//= mapService.getCalendarDates();
    private Map<String, List<BigInteger>> stopNameToStopId;//= mapService.getStopNameToStopId();
    private Map<BigInteger, List<BigInteger>> serviceIdToTripId; //= mapService.getServiceIdToTripId();
    private List<String> stationList; //= mapService.getStationList();


    public SearchService() {
    }
    public SearchService(MapService mapService){
        this.mapService = mapService;
    }

    public void setUp() {
       mapService.createMaps();
        stopIdTostopTimes = mapService.getStopIdTostopTimes();
        TripIdTotrips = mapService.getTripIdTotrips();
        routes = mapService.getRoutes();
        calendarDates = mapService.getCalendarDates();
        stopNameToStopId = mapService.getStopNameToStopId();
        serviceIdToTripId = mapService.getServiceIdToTripId();
        stationList = mapService.getStationList();
    }
    public void testSetUp(){
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
        List<String> stations = new ArrayList<>();
        if(stationList == null){
            return stations;
        }else{
            return stationList;
        }

    }

//    private Set<BigInteger> getStationIdWithNames(String name) {
//        Set<BigInteger> stationIds = new TreeSet<>();
//        if (mapService.getStopNameToStopId().containsKey(name)) {
//            stationIds.addAll(mapService.getStopNameToStopId().get(name));
//        }
//        return stationIds;
//    }
//
//    private Set<StopTime> getStopTimesWithStationId(BigInteger stopId) {
//        Set<StopTime> stopTimes = new TreeSet<>();
//        if (mapService.getStopIdTostopTimes().containsKey(stopId)) {
//            stopTimes.addAll(mapService.getStopIdTostopTimes().get(stopId));
//        }
//        return stopTimes;
//    }
//
//    private Set<Trip> getTripWithTripId(BigInteger tripId) {
//        Set<Trip> tripIdList = new TreeSet<>();
//        if (mapService.getTripIdTotrips().containsKey(tripId)) {
//            tripIdList.add(mapService.getTripIdTotrips().get(tripId));
//        }
//        return tripIdList;
//    }
//
//    private Set<BigInteger> getServiceIdWithTripId(BigInteger tripId) {
//        Set<BigInteger> calendarId = new TreeSet<>();
//        Trip trip = mapService.getTripIdTotrips().get(tripId);
//        if (mapService.getTripIdTotrips().containsKey(tripId)) {
//            calendarId.add(new BigInteger(trip.getService_id()));
//        }
//        return calendarId;
//    }
//
//    private Set<BigInteger> getTodayServiceIdWithServiceId(BigInteger serviceID, String date) {
//        Set<BigInteger> calendarDateId = new TreeSet<>();
//        if (mapService.getCalendarDates().containsKey(serviceID)) {
//            List<CalendarDate> calendarDateList = mapService.getCalendarDates().get(serviceID);
//            for (CalendarDate calendarDate : calendarDateList) {
//                if (calendarDate.getDate().equals(date)) {
//                    calendarDateId.add(new BigInteger(calendarDate.getService_id()));
//                }
//            }
//        }
//        return calendarDateId;
//    }
//
//    private List<Trip> getTripsWithServiceId(BigInteger serviceId) {
//        List<Trip> tripList = new ArrayList<>();
//        List<BigInteger> tripIds = new ArrayList<>();
//        if (mapService.getServiceIdToTripId().containsKey(serviceId)) {
//            for (int i = 0; i < mapService.getServiceIdToTripId().get(serviceId).size(); i++) {
//                // System.out.println("size: " + mapService.getServiceIdToTripId().get(serviceId).size());
//                //System.out.println(i);
//                if (!tripIds.contains(mapService.getServiceIdToTripId().get(serviceId).get(i))) {
//                    tripIds.add(mapService.getServiceIdToTripId().get(serviceId).get(i));
//                }
//            }
//            //tripIdList.add(TripIdTotrips.getserviceIdToTripId.get(serviceId));
//        }
//        for (BigInteger tripId : tripIds) {
//            if (mapService.getTripIdTotrips().containsKey(tripId)) {
//                tripList.add(mapService.getTripIdTotrips().get(tripId));
//            }
//        }
//        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
//        return tripList;
//
//    }
//
//    private Set<StopTime> getStopTimeByTrips(Set<Trip> trips, Set<StopTime> stopTimes) {
//        //System.out.println(trips.size());
//        Set<StopTime> stopTimeList = new TreeSet<>();
//        for (StopTime stopTime : stopTimes) {
//            for (int i = 0; i < trips.size(); i++) {
//                if (stopTime.getTrip_id().equals(trips.stream().toList().get(i).getTrip_id())) {
//                    stopTimeList.add(stopTime);
//                }
//            }
//        }
//        return stopTimeList;
//    }
//
//
//    private List<Departure> getDeparturesWithStopTime(String timeNow, Set<StopTime> stopTimes) {
//        List<Departure> departures = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            System.out.println(i);
//            Trip trip = mapService.getTripIdTotrips().get(new BigInteger(stopTimes.stream().toList().get(i).getTrip_id()));
//            Route route = mapService.getRoutes().get(trip.getRoute_id());
//
//            String parseTime = stopTimes.stream().toList().get(i).getDeparture_time();
//            if (parseTime.compareTo(timeNow) > 0) {
//                String destination = stopTimes.stream().toList().get(i).getStop_headsign();
//                String departureTime = stopTimes.stream().toList().get(i).getDeparture_time();
//                String vehicleType = route.getRoute_type();
//                String linenumber = route.getRoute_short_name();
//                Departure departure = new Departure(destination, departureTime, vehicleType, linenumber);
//                departures.add(departure);
//            }
//
//        }
//        return departures;
//    }

    public Set<BigInteger> getStationIdWithName(String name) {
        Set<BigInteger> stationIds = new HashSet<>();
        if (stopNameToStopId.containsKey(name.toLowerCase())) {
            stationIds.addAll(stopNameToStopId.get(name.toLowerCase()));
        }
        return stationIds;
    }

    public Set<StopTime> getStopTimesWithStationId(BigInteger stopId) {
        Set<StopTime> stopTimes = new HashSet<>();
        if (stopIdTostopTimes.containsKey(stopId)) {
            stopTimes.addAll(stopIdTostopTimes.get(stopId));
        }
        return stopTimes;
    }

    public Map<String, List<StopTime>> makeMap(Set<StopTime> stopTimeslist) {
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
        Set<Trip> tripIdList = new HashSet<>();
        if (TripIdTotrips.containsKey(tripId)) {
            tripIdList.add(TripIdTotrips.get(tripId));
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripIdList;
    }

    public Set<BigInteger> getServiceIdWithTripId(BigInteger tripId) {
        Set<BigInteger> calendarId = new HashSet<>();
        Trip trip = TripIdTotrips.get(tripId);
        if (TripIdTotrips.containsKey(tripId)) {
            calendarId.add(new BigInteger(trip.getService_id()));
        }
        return calendarId;
    }

    public Set<BigInteger> getTodayServiceIdWithServiceId(BigInteger serviceID, String date) {
        Set<BigInteger> calendarDateId = new HashSet<>();
        if (calendarDates.containsKey(serviceID)) {
            List<CalendarDate> calendarDateList = calendarDates.get(serviceID);
            for (CalendarDate calendarDate : calendarDateList) {
                if (calendarDate.getDate().equals(date)) {
                    calendarDateId.add(new BigInteger(calendarDate.getService_id()));
                }
            }
        }
        return calendarDateId;
    }

    public Set<Trip> getTripsWithServiceId(BigInteger serviceId) {
        Set<Trip> tripList = new HashSet<>();
        List<BigInteger> tripIds = new ArrayList<>();
        if (serviceIdToTripId.containsKey(serviceId)) {
            tripIds.addAll(serviceIdToTripId.get(serviceId));

            //tripIdList.add(TripIdTotrips.getserviceIdToTripId.get(serviceId));
        }
        for (BigInteger tripId : tripIds) {
            if (TripIdTotrips.containsKey(tripId)) {
                tripList.add(TripIdTotrips.get(tripId));
            }
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripList;

    }

    public Set<StopTime> getStopTimeWithTrip(Set<Trip> trips, Map<String, List<StopTime>> map) {
        //System.out.println(trips.size());
        Set<StopTime> stopTimeList = new HashSet<>();
        for (Trip trip : trips) {
            if (map.containsKey(trip.getTrip_id())) {
                stopTimeList.addAll(map.get(trip.getTrip_id()));
            }
        }
        return stopTimeList;
    }

    public List<Departure> getDeparturesWithStopTime(String timeNow, Set<StopTime> stopTimes) {
        List<Departure> departures = new ArrayList<>();
        for (StopTime stopTime : stopTimes) {
            Trip trip = TripIdTotrips.get(new BigInteger(stopTime.getTrip_id()));
            Route route = routes.get(trip.getRoute_id());

            String parseTime = stopTime.getDeparture_time();
            if (parseTime.compareTo(timeNow) > 0) {
                String destination = stopTime.getStop_headsign();
                String departureTime = stopTime.getDeparture_time();
                String vehicleType = route.getRoute_type();
                String linenumber = route.getRoute_short_name();
                Departure departure = new Departure(destination, departureTime, vehicleType, linenumber);
                departures.add(departure);
            }

        }
        return departures;
    }


    public List<Departure> getDeparturesFromStopName(String stopName, String dateTime) {
        String date = dateTime.substring(0,8); //.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String time = dateTime.substring(9); //ow.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        List<Departure> departuresList = new ArrayList<>();
        Set<StopTime> stopTimesList = new HashSet<>();
        Set<Trip> tripList = new HashSet<>();
        Set<BigInteger> serviceIdList = new HashSet<>();
        Set<BigInteger> serviceIdListToday = new HashSet<>();

        if (stopNameToStopId == null) {
            return departuresList;
        }
        Set<BigInteger> StationIdList = getStationIdWithName(stopName);
        if (StationIdList.isEmpty()) {
            return departuresList;
        }
        findStopTimesFromStationId(StationIdList, stopTimesList);

        Map<String, List<StopTime>> stopTimeMap = makeMap(stopTimesList);
        getTripsFromFromStopTimes(stopTimesList, tripList);
        getServiceIdFromTrips(tripList, serviceIdList);

        getServiceIdForTodayFromServiceIds(serviceIdList, serviceIdListToday, date);

        Set<Trip> tripsFromService = new HashSet<>();
        getTripsFromTodaysServiceIds(serviceIdListToday, tripsFromService);
        tripList.retainAll(tripsFromService);

        Set<StopTime> stopTimesFromService = getStopTimeWithTrip(tripList, stopTimeMap);
        departuresList = getDeparturesWithStopTime(time, stopTimesFromService);

        return departuresList;
    }

    private void getTripsFromTodaysServiceIds(Set<BigInteger> serviceIdListToday, Set<Trip> tripsFromService) {
        for (BigInteger serviceId : serviceIdListToday) {
            tripsFromService.addAll(getTripsWithServiceId(serviceId));
        }
    }

    private void getServiceIdForTodayFromServiceIds(Set<BigInteger> serviceIdList, Set<BigInteger> serviceIdListToday, String date) {
        //get service Id that is connecet to trip id
        for (BigInteger serviceId : serviceIdList) {
            serviceIdListToday.addAll(getTodayServiceIdWithServiceId(serviceId, date));
        }
    }

    private void getServiceIdFromTrips(Set<Trip> tripList, Set<BigInteger> serviceIdList) {
        for (Trip tripId : tripList) {
            // routeIdList.addAll(searchService.getRouteId(tripId));
            serviceIdList.addAll(getServiceIdWithTripId(new BigInteger(tripId.getTrip_id())));
        }
    }

    private void getTripsFromFromStopTimes(Set<StopTime> stopTimesList, Set<Trip> tripList) {
        // get all Trips from the StopTimes
        for (StopTime stopTime : stopTimesList) {
            tripList.addAll(getTripWithTripId(new BigInteger(stopTime.getTrip_id())));
        }
    }

    private void findStopTimesFromStationId(Set<BigInteger> StationIdList, Set<StopTime> stopTimesList) {
        //get all stopTimes for station Id
        for (BigInteger stationId : StationIdList) {
            stopTimesList.addAll(getStopTimesWithStationId(stationId));
        }
    }
}
