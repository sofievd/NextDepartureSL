package se.iths.nextdeparturesl.service;

import se.iths.nextdeparturesl.model.VehiclePosition;

import java.util.ArrayList;
import java.util.List;

public class GtfsVehiclePositionHolder {
    private List<VehiclePosition> positions;
    private static GtfsVehiclePositionHolder instance;


    private GtfsVehiclePositionHolder() {
    }

    public static GtfsVehiclePositionHolder getInstance() {
        if (instance == null) {
            instance = new GtfsVehiclePositionHolder();
        }
        return instance;
    }

    public List<VehiclePosition> getPositions() {
        return new ArrayList<>(positions);
    }

    public void setVehiclePositions(List<VehiclePosition> vehiclePositions) {
        positions = vehiclePositions;

    }

}
