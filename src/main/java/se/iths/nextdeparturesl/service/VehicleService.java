package se.iths.nextdeparturesl.service;


import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.ApiDownloader;
import se.iths.nextdeparturesl.util.GtfsRealtimeDownloadTask;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class VehicleService {
    private List<VehiclePosition> vehicleList;
    private GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
    private final Map<String, Trip> tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
    private final Map<String, Route> routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();
    private GtfsVehiclePositionHolder gtfsVehiclePositionHolder;

    public VehicleService() {
        setup();
    }

    public void setup() {
        ApiDownloader downloader = new ApiDownloader();
        byte[] vehicleBytes = downloader.downloadGtfsRealTimeVehiclePosition();
        GtfsVehiclePositionHolder gtfsVehiclePositionHolder = GtfsVehiclePositionHolder.getInstance(vehicleBytes);
        vehicleList = gtfsVehiclePositionHolder.getVehicles();
    }

    public void update() {
        long delay = 5000;
        long period = 5000;
        new Timer().scheduleAtFixedRate(new GtfsRealtimeDownloadTask(), delay, period);
        gtfsVehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();
    }

    public void updateVehiclePositionWithType() {
        for (VehiclePosition vehicle : vehicleList) {
            String tripId = vehicle.getId();
            if (tripIdToTrips.containsKey(tripId)) {
                Trip trip = tripIdToTrips.get(tripId);
                String routeId = trip.getRouteId();
                Route route = routeIdToRoutes.get(routeId);
                int type = route.getType();
                String routeName = route.getLongName();
                String lineNumber = route.getShortName();
                String description = route.getDesc();
                vehicle.setType(type);
                vehicle.setRouteName(routeName);
                vehicle.setRouteDescription(description);
                vehicle.setLineNumber(lineNumber);
            }
            //System.out.println(vehicle);
        }
    }


    public List<VehiclePosition> getVehicles() {
        updateVehiclePositionWithType();
        return vehicleList;
    }

}
