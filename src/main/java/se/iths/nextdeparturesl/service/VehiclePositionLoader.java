package se.iths.nextdeparturesl.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.model.VehiclePosition;
import se.iths.nextdeparturesl.util.GtfsRealtimeDownloadTask;
import se.iths.nextdeparturesl.DTO.VehiclePositionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class VehiclePositionLoader {
    private static final Logger log = LogManager.getLogger();
    private GtfsDataHolder gtfsDataHolder; //= GtfsDataHolder.getInstance();
    private  Map<String, Trip> tripIdToTrips;// gtfsDataHolder.getTripIdToTrips();
    private  Map<String, Route> routeIdToRoutes;// = gtfsDataHolder.getRouteIdToRoutes();

    public VehiclePositionLoader() {
    }

    public void startUpdate() {
        gtfsDataHolder = GtfsDataHolder.getInstance();
        tripIdToTrips= gtfsDataHolder.getTripIdToTrips();
        routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();
        GtfsRealtimeDownloadTask task = new GtfsRealtimeDownloadTask();
        long delay = 0;
        long period = 2000;
        new Timer().schedule(task, delay, period);
    }

    public List<VehiclePositionDTO> getVehiclePositions() {
        log.info("trying to get vehicle positions");
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
        Trip trip = tripIdToTrips.get(tripId);
        String routeId = trip.getRouteId();
        Route route = routeIdToRoutes.get(routeId);
        int type = route.getType();
        String routeName = route.getLongName();
        String lineNumber = route.getShortName();
        String description = route.getDesc();
        VehiclePositionDTO vehiclePositionDTO = new VehiclePositionDTO(vehicle.getId(),
                vehicle.getLatitude(), vehicle.getLongitude(), vehicle.getBearing(), vehicle.getSpeed());
        vehiclePositionDTO.setType(type);
        vehiclePositionDTO.setRouteName(routeName);
        vehiclePositionDTO.setRouteDescription(description);
        vehiclePositionDTO.setLineNumber(lineNumber);
        return vehiclePositionDTO;
    }

}
