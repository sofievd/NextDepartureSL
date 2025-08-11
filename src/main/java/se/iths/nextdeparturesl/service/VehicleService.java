package se.iths.nextdeparturesl.service;


import se.iths.nextdeparturesl.view.VehiclePosition;

import java.util.List;

public class VehicleService {
    private GtfsVehiclePositionHolder gtfsVehiclePositionHolder = new GtfsVehiclePositionHolder();

    public VehicleService() {
    }

    public List<VehiclePosition> getVehiclePositions() {
        return gtfsVehiclePositionHolder.getVehicles();
    }


}
