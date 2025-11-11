package se.iths.nextdeparturesl.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.dto.StopDTO;
import se.iths.nextdeparturesl.dto.VehiclePositionDTO;
import se.iths.nextdeparturesl.dto.VehicleStopsDTO;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.model.VehiclePosition;
import se.iths.nextdeparturesl.util.GtfsRealtimeDownloadTask;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VehiclePositionLoader {
    private static final Logger log = LogManager.getLogger();
    private Map<String, Trip> tripIdToTrips;
    private Map<String, Route> routeIdToRoutes;
    private Map<String, List<StopTime>> tripIdToStopTimes;
    private Map<String, String> stopIdToStopName;

    public void startUpdate() {
        GtfsRealtimeDownloadTask task = new GtfsRealtimeDownloadTask();
        long delay = 0;
        long period = 2000;
        new Timer().schedule(task, delay, period);
    }

    public List<VehiclePositionDTO> getVehiclePositions() {
        log.info("trying to get vehicle positions");
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        GtfsVehiclePositionHolder gtfsVehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();
        List<VehiclePosition> vehiclePositionList = gtfsVehiclePositionHolder.getPositions();

        List<VehiclePositionDTO> results = new ArrayList<>();

        for (VehiclePosition vehicle : vehiclePositionList) {
            String tripId = vehicle.getId();
            if (tripIdToTrips.containsKey(tripId)) {
                VehiclePositionDTO vehiclePositionDTO = convertVehiclePosition(vehicle, tripId);
                results.add(vehiclePositionDTO);
            }
        }
        return results;
    }

    private VehiclePositionDTO convertVehiclePosition(VehiclePosition vehicle, String tripId) {
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();

        Trip trip = tripIdToTrips.get(tripId);
        String routeId = trip.getRouteId();
        Route route = routeIdToRoutes.get(routeId);
        int type = route.getType();
        String lineNumber = route.getShortName();
        VehiclePositionDTO vehiclePositionDTO = new VehiclePositionDTO(vehicle.getId(),
                vehicle.getLatitude(), vehicle.getLongitude(), vehicle.getBearing(), vehicle.getSpeed());
        vehiclePositionDTO.setType(type);
        vehiclePositionDTO.setLineNumber(lineNumber);
        vehiclePositionDTO.setTimestamp(vehicle.getTime());
        return vehiclePositionDTO;
    }

    private String getDestination(String tripId) {
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        tripIdToStopTimes = gtfsDataHolder.getTripIdToStopTimes();

        String destination = "";
        List<String> destinations = new ArrayList<>();
        if (tripIdToStopTimes.containsKey(tripId)) {
            List<StopTime> stopTimes = tripIdToStopTimes.get(tripId);
            for (StopTime stopTime : stopTimes) {
                if (!destinations.contains(stopTime.getStopHeadsign())) {
                    destinations.add(stopTime.getStopHeadsign());
                }
            }
        }
        if (!destinations.isEmpty()) {
            destination = destinations.get(0);
        }
        return destination;
    }

    public VehicleStopsDTO getNextStops(String tripId, long searchDateTime) {
        log.info("Finding next stops");
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();

        Trip trip = tripIdToTrips.get(tripId);
        String routeId = trip.getRouteId();
        Route route = routeIdToRoutes.get(routeId);
        String routeName = route.getLongName();
        String lineNumber = route.getShortName();
        String description = route.getDesc();

        List<StopDTO> stopDTOList = getNextStopDto(tripId, searchDateTime);

        VehicleStopsDTO vehicleStopsDTO = new VehicleStopsDTO();
        vehicleStopsDTO.setNextStops(stopDTOList);
        vehicleStopsDTO.setTripId(tripId);
        vehicleStopsDTO.setDestination(getDestination(tripId));
        vehicleStopsDTO.setLineNumber(lineNumber);
        vehicleStopsDTO.setRouteName(routeName);
        vehicleStopsDTO.setRouteDescription(description);

        return vehicleStopsDTO;
    }

    private List<StopDTO> getNextStopDto(String tripId, long searchTimestamp) {
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        tripIdToStopTimes= gtfsDataHolder.getTripIdToStopTimes();

        Instant instant = Instant.ofEpochSecond(searchTimestamp);
        LocalDateTime searchDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        List<StopDTO> nextStops = new ArrayList<>();
        log.info("trying to get next stops");
        if (tripIdToStopTimes.containsKey(tripId)) {
            List<StopTime> stopTimes = tripIdToStopTimes.get(tripId);
            for (StopTime stopTime : stopTimes) {
                nextStops.add(createStopDto(searchDateTime, stopTime));
            }
        }
        removeStopDTOsBeforeSearchDateTime(searchDateTime, nextStops);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
        nextStops.sort(Comparator.comparing(stop -> LocalDateTime.parse(stop.getArrivalTime(), formatter)));

        return nextStops;
    }

    private StopDTO createStopDto(LocalDateTime searchDateTime, StopTime stopTime) {
        StopDTO stopDTO = new StopDTO();
        LocalDate searchDate = searchDateTime.toLocalDate();
        String arrival = formatOffsetTime(stopTime.getArrivalTime(), searchDate);
        String name = getStopName(stopTime.getStopId());
        stopDTO.setArrivalTime(arrival);
        stopDTO.setStopName(name);
        return stopDTO;
    }


    private String formatOffsetTime(int offsetTime, LocalDate date) {
        LocalDateTime dateTime = date.atTime(LocalTime.of(0, 0, 0)).plusSeconds(offsetTime);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"));
    }

    private void removeStopDTOsBeforeSearchDateTime(LocalDateTime searchDateTime, List<StopDTO> stopDTOList) {
        String earliestTimeString = searchDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"));
        stopDTOList.removeIf(departure -> departure.getArrivalTime().compareTo(earliestTimeString) < 0);
    }

    private String getStopName(String stopId) {
        GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
        stopIdToStopName = gtfsDataHolder.getStopIdToStopName();

        String stopName = "";
        if (stopIdToStopName.containsKey(stopId)) {
            stopName = stopIdToStopName.get(stopId);
        }
        return stopName;
    }
}
