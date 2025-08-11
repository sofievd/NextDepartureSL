package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.util.GtfsVehiclePositionHandler;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.io.File;
import java.util.List;


public class GtfsVehiclePositionHolder {
    private List<VehiclePosition> vehicleList;
    private GtfsVehiclePositionHandler vehiclePositionHandler = new GtfsVehiclePositionHandler(
            new File(getClass().getClassLoader().getResource("vehiclePositions.pb").getFile()));


    public GtfsVehiclePositionHolder() {
        this.vehicleList = vehiclePositionHandler.readVehiclePositions();
    }


    public List<VehiclePosition> getVehicles() {
        return vehicleList;
    }

    public void setVehicles(List<VehiclePosition> vehicles) {
        this.vehicleList = vehicles;
    }
}
