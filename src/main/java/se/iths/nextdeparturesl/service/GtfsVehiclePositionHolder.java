package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.GtfsVehiclePositionHandler;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.util.List;
import java.util.Map;


public class GtfsVehiclePositionHolder {
    private List<VehiclePosition> vehicleList;
    private byte[] vehiclePositionBytes;
    private static GtfsVehiclePositionHolder instance;
    private final GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
    private final Map<String, Trip> tripIdToTrips = gtfsDataHolder.getTripIdToTrips();
    private final Map<String, Route> routeIdToRoutes = gtfsDataHolder.getRouteIdToRoutes();


    private GtfsVehiclePositionHolder(byte[] vehiclePositionBytes) {
        GtfsVehiclePositionHandler vehiclePositionHandler = new GtfsVehiclePositionHandler(vehiclePositionBytes);
        this.vehicleList = vehiclePositionHandler.getVehiclePositionsList();
    }

    public static GtfsVehiclePositionHolder getInstance(byte[] vehiclePositionBytes) {
        if(instance == null) {
            instance = new GtfsVehiclePositionHolder(vehiclePositionBytes);
        }
        return instance;
    }
    public static GtfsVehiclePositionHolder getInstance() {
        return instance;
    }

    public List<VehiclePosition> getVehicles() {
        return vehicleList;
    }

    public void setVehiclePositionBytes(byte[] vehiclePositionBytes) {
        this.vehiclePositionBytes = vehiclePositionBytes;
    }

}
