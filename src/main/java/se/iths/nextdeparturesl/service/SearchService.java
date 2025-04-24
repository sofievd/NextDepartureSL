package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.view.Departure;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// TODO: adding logging
//TODO: error handling
//TODO: refactoring
//TODO: addning tests
public class SearchService {

    private final MapService mapService = new MapService();


    private final Map<BigInteger, List<StopTime>> stopIdTostopTimes = mapService.getStopIdTostopTimes();
    private final Map<BigInteger, Trip> TripIdTotrips = mapService.getTripIdTotrips();
    private final Map<String, Route> routes = mapService.getRoutes();
    private final Map<BigInteger, List<CalendarDate>> calendarDates = mapService.getCalendarDates();
    private final Map<String, List<BigInteger>> stopNameToStopId = mapService.getStopNameToStopId();
    private final Map<BigInteger, List<BigInteger>> serviceIdToTripId = mapService.getServiceIdToTripId();
    private final List<String> stationList = mapService.getStationList();


    public List<String> getStationList() {
        return stationList;
    }

    private Set<BigInteger> getStationIdWithNames(String name) {
        Set<BigInteger> stationIds = new TreeSet<>();
        if (mapService.getStopNameToStopId().containsKey(name)) {
            stationIds.addAll(mapService.getStopNameToStopId().get(name));
        }
        return stationIds;
    }

    private Set<StopTime> getStopTimesWithStationId(BigInteger stopId) {
        Set<StopTime> stopTimes = new TreeSet<>();
        if (mapService.getStopIdTostopTimes().containsKey(stopId)) {
            stopTimes.addAll(mapService.getStopIdTostopTimes().get(stopId));
        }
        return stopTimes;
    }

    private Set<Trip> getTripWithTripId(BigInteger tripId) {
        Set<Trip> tripIdList = new TreeSet<>();
        if (mapService.getTripIdTotrips().containsKey(tripId)) {
            tripIdList.add(mapService.getTripIdTotrips().get(tripId));
        }
        return tripIdList;
    }

    private Set<BigInteger> getServiceIdWithTripId(BigInteger tripId) {
        Set<BigInteger> calendarId = new TreeSet<>();
        Trip trip = mapService.getTripIdTotrips().get(tripId);
        if (mapService.getTripIdTotrips().containsKey(tripId)) {
            calendarId.add(new BigInteger(trip.getService_id()));
        }
        return calendarId;
    }

    private Set<BigInteger> getTodayServiceIdWithServiceId(BigInteger serviceID, String date) {
        Set<BigInteger> calendarDateId = new TreeSet<>();
        if (mapService.getCalendarDates().containsKey(serviceID)) {
            List<CalendarDate> calendarDateList = mapService.getCalendarDates().get(serviceID);
            for (CalendarDate calendarDate : calendarDateList) {
                if (calendarDate.getDate().equals(date)) {
                    calendarDateId.add(new BigInteger(calendarDate.getService_id()));
                }
            }
        }
        return calendarDateId;
    }

    private List<Trip> getTripsWithServiceId(BigInteger serviceId) {
        List<Trip> tripList = new ArrayList<>();
        List<BigInteger> tripIds = new ArrayList<>();
        if (mapService.getServiceIdToTripId().containsKey(serviceId)) {
            for (int i = 0; i < mapService.getServiceIdToTripId().get(serviceId).size(); i++) {
                // System.out.println("size: " + mapService.getServiceIdToTripId().get(serviceId).size());
                //System.out.println(i);
                if (!tripIds.contains(mapService.getServiceIdToTripId().get(serviceId).get(i))) {
                    tripIds.add(mapService.getServiceIdToTripId().get(serviceId).get(i));
                }
            }
            //tripIdList.add(TripIdTotrips.getserviceIdToTripId.get(serviceId));
        }
        for (BigInteger tripId : tripIds) {
            if (mapService.getTripIdTotrips().containsKey(tripId)) {
                tripList.add(mapService.getTripIdTotrips().get(tripId));
            }
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripList;

    }

    private Set<StopTime> getStopTimeByTrips(Set<Trip> trips, Set<StopTime> stopTimes) {
        //System.out.println(trips.size());
        Set<StopTime> stopTimeList = new TreeSet<>();
        for (StopTime stopTime : stopTimes) {
            for (int i = 0; i < trips.size(); i++) {
                if (stopTime.getTrip_id().equals(trips.stream().toList().get(i).getTrip_id())) {
                    stopTimeList.add(stopTime);
                }
            }
        }
        return stopTimeList;
    }


    private List<Departure> getDeparturesWithStopTime(String timeNow, Set<StopTime> stopTimes) {
        List<Departure> departures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            Trip trip = mapService.getTripIdTotrips().get(new BigInteger(stopTimes.stream().toList().get(i).getTrip_id()));
            Route route = mapService.getRoutes().get(trip.getRoute_id());

            String parseTime = stopTimes.stream().toList().get(i).getDeparture_time();
            if (parseTime.compareTo(timeNow) > 0) {
                String destination = stopTimes.stream().toList().get(i).getStop_headsign();
                String departureTime = stopTimes.stream().toList().get(i).getDeparture_time();
                String vehicleType = route.getRoute_type();
                String linenumber = route.getRoute_short_name();
                Departure departure = new Departure(destination, departureTime, vehicleType, linenumber);
                departures.add(departure);
            }

        }
        return departures;
    }

//    public List<Departure> findNextDeparture(String stopName) {
//        List<Departure> nextDepartures = new ArrayList<>();
//        Set<BigInteger> stationIdList = getStationIdWithName(stopName);
//        Set<StopTime> stopTimesFromStationId = new TreeSet<>();
//        Set<Trip> tripsFromStationId = new TreeSet<>();
//        Set<BigInteger> serviceIdFromStationId = new TreeSet<>();
//        Set<BigInteger> serviceIdListToday = new TreeSet<>();
//        Set<Trip> tripsFromService = new TreeSet<>();
//
//        LocalDateTime now = LocalDateTime.now();
//        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        //get StopTime that are connected to the stop id
//        if (!stationIdList.isEmpty()) {
//            System.out.println("steg 1.");
//            stationIdList.forEach(stationId -> stopTimesFromStationId.addAll(getStopTimesWithStationId(stationId)));
////             for (BigInteger stationId : stationIdList) {
////                 stopTimesFromStationId.addAll(getStopTimesWithStationId(stationId));
////             }
//        } else {
//            return nextDepartures;
//        }
//
//        if (!stopTimesFromStationId.isEmpty()) {
//            System.out.println("Steg 2!");
//            for (StopTime stopTime : stopTimesFromStationId) {
//                tripsFromStationId.addAll(getTripWithTripId(new BigInteger(stopTime.getTrip_id())));
//            }
//        } else {
//            return nextDepartures;
//        }
//
//        if (!tripsFromStationId.isEmpty()) {
//            System.out.println("Steg! 3");
//            for (Trip tripId : tripsFromStationId) {
//                serviceIdFromStationId.addAll(getServiceIdWithTripId(new BigInteger(tripId.getTrip_id())));
//            }
//        }
//
//        if (!serviceIdFromStationId.isEmpty()) {
//            System.out.println("Steg 4!");
//            //get service Id that is connecet to trip id
//            for (BigInteger serviceId : serviceIdFromStationId) {
//                serviceIdListToday.addAll(getTodayServiceIdWithServiceId(serviceId, date));
//            }
//        } else {
//            return nextDepartures;
//        }
//
//        if (!serviceIdListToday.isEmpty()) {
//            System.out.println("Steg 5!");
//            System.out.println(serviceIdListToday.size());
//            for (BigInteger serviceId : serviceIdListToday) {
//                tripsFromService.addAll(getTripsWithServiceId(serviceId));
//            }
//        } else {
//            return nextDepartures;
//        }
//
//        if (!tripsFromService.isEmpty()) {
//            System.out.println("nästan klar !");
//            tripsFromStationId.retainAll(tripsFromService);
//            Set<StopTime> stopTimesFromService = getStopTimeByTrips(tripsFromStationId, stopTimesFromStationId);
//            nextDepartures = getDeparturesWithStopTime(getTime(now), stopTimesFromService);
//        } else {
//            return nextDepartures;
//        }
//
//        return nextDepartures;
//    }

    public Set<BigInteger> getStationIdWithName(String name) {
        Set<BigInteger> stationIds = new HashSet<>();
        if (stopNameToStopId.containsKey(name)) {
            //System.out.println(stopNameToStopId.get(name));
            stationIds.addAll(stopNameToStopId.get(name));
        }
        return stationIds;

    }

    public Set<StopTime> getStopTimesList(BigInteger stopId) {
        Set<StopTime> stopTimes = new HashSet<>();
        if (stopIdTostopTimes.containsKey(stopId)) {
            stopTimes.addAll(stopIdTostopTimes.get(stopId));
        }
        return stopTimes;
    }

    private Map<String, List<StopTime>> makeMap(Set<StopTime> stopTimeslist) {
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

    public Set<Trip> getTripByTripId(BigInteger tripId) {
        Set<Trip> tripIdList = new HashSet<>();
        if (TripIdTotrips.containsKey(tripId)) {
            tripIdList.add(TripIdTotrips.get(tripId));
        }
        //stopTimeList.forEach(stop -> System.out.println(stop.getStop_headsign()));
        return tripIdList;
    }
    public Set<BigInteger> getCalendarId(BigInteger tripId) {
        Set<BigInteger> calendarId = new HashSet<>();
        Trip trip = TripIdTotrips.get(tripId);
        if (TripIdTotrips.containsKey(tripId)) {
            calendarId.add(new BigInteger(trip.getService_id()));
        }
        return calendarId;
    }

    public Set<BigInteger> getCalendarDateId(BigInteger serviceID, String date) {
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

    public Set<Trip> getTripsByServiceId(BigInteger serviceId) {
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

    public Set<StopTime> getStopTimeByTrip(Set<Trip> trips, Map<String, List<StopTime>> map) {
        //System.out.println(trips.size());
        Set<StopTime> stopTimeList = new HashSet<>();
        for (Trip trip : trips) {
            if (map.containsKey(trip.getTrip_id())) {
                stopTimeList.addAll(map.get(trip.getTrip_id()));
            }
        }
        return stopTimeList;
    }

    public List<Departure> getDeparturesFromTripId(String timeNow, Set<StopTime> stopTimes) {
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


    public List<Departure> departures(String stopName) {
        Set<BigInteger> StationIdList = getStationIdWithName(stopName);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("station:" + StationIdList.size());

        Set<StopTime> stopTimesList = new HashSet<>();
        Set<Trip> tripList = new HashSet<>();
        Set<BigInteger> serviceIdList = new HashSet<>();
        if(!StationIdList.isEmpty()){
            //get all stopTimes for station Id
            for (BigInteger stationId : StationIdList) {
                stopTimesList.addAll(getStopTimesList(stationId));
            }
        }

        Map<String, List<StopTime>> stopTimeMap = makeMap(stopTimesList);

        System.out.println("stopTimesList:" + stopTimesList.size());
        // get all Trips from the StopTimes
        for (StopTime stopTime : stopTimesList) {
            tripList.addAll(getTripByTripId(new BigInteger(stopTime.getTrip_id())));
        }

        // getting serivce Ids from the trips
        for (Trip tripId : tripList) {
            // routeIdList.addAll(searchService.getRouteId(tripId));
            serviceIdList.addAll(getCalendarId(new BigInteger(tripId.getTrip_id())));
        }

        Set<BigInteger> serviceIdListToday = new HashSet<>();

        // System.out.println("route: " + routeIdList.size());
        System.out.println("service: " + serviceIdList.size());

        //get service Id that is connecet to trip id
        for (BigInteger serviceId : serviceIdList) {
            serviceIdListToday.addAll(getCalendarDateId(serviceId, date));
        }
        System.out.println("service today: " + serviceIdListToday.size());

        //getting all trips that have the serivce Id of today
        Set<Trip> tripsFromService = new HashSet<>();
        for (BigInteger serviceId : serviceIdListToday) {
            tripsFromService.addAll(getTripsByServiceId(serviceId));
        }

        System.out.println("Trips from station: " + tripList.size());
        System.out.println("trips from serivce: " + tripsFromService.size());

        tripList.retainAll(tripsFromService);

        System.out.println("new TripId list: " + tripList.size());

        System.out.println(LocalDateTime.now());

        // get a list of all stopTimes with the new tripI's
        Set<StopTime> stopTimesFromService = getStopTimeByTrip(tripList, stopTimeMap);
        System.out.println("stoptime with trip ID " + stopTimesFromService.size());
        List<Departure> departuresList = getDeparturesFromTripId(time, stopTimesFromService);

        System.out.println(LocalDateTime.now());
        return departuresList;
    }
}
