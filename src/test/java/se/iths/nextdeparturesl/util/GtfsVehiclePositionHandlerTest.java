package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GtfsVehiclePositionHandlerTest {
    GtfsVehiclePositionHandler handler = new GtfsVehiclePositionHandler(
            new File(getClass().getClassLoader().getResource("vehiclePositions.pb").getFile()));

    @Test
    void readVehiclePositions() {
        List<VehiclePosition> vehicleList = handler.readVehiclePositions();
        assertNotNull(vehicleList);
        assertFalse(vehicleList.isEmpty());
    }
}