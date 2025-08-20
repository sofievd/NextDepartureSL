package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GtfsVehiclePositionHolderTest {
    private GtfsVehiclePositionHolder gtfsVehiclePositionHolder;

    @BeforeEach
    void setUp() {
        gtfsVehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();
    }



    @Test
    void getVehicles() {
        List<VehiclePosition> vehiclePositionList = gtfsVehiclePositionHolder.getVehicles();
        assertNotNull(vehiclePositionList);
    }
}