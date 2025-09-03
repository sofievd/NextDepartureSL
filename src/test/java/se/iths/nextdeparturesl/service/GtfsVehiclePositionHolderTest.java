package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.VehiclePosition;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GtfsVehiclePositionHolderTest {
    private GtfsVehiclePositionHolder gtfsVehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();

    @AfterEach
    void tearDown() {
        gtfsVehiclePositionHolder.setVehiclePositions(Collections.emptyList());
    }

    @Test
    void setVehiclePositionBytes() {
        List<VehiclePosition> testPositions = List.of(new VehiclePosition("1", 12.2, 12.5, 11.2, 10.0),
                new VehiclePosition("2", 12.3, 12.5, 11.5, 10.0));

        gtfsVehiclePositionHolder.setVehiclePositions(testPositions);
        List<VehiclePosition> vehiclePositionList = gtfsVehiclePositionHolder.getPositions();
        assertNotNull(vehiclePositionList);
        assertEquals(2, vehiclePositionList.size());

        testPositions = List.of(new VehiclePosition("3", 12.2, 12.5, 11.2, 10.0),
                new VehiclePosition("4", 12.3, 12.5, 11.5, 10.0));

        gtfsVehiclePositionHolder.setVehiclePositions(testPositions);
        vehiclePositionList = gtfsVehiclePositionHolder.getPositions();

        assertEquals(2, vehiclePositionList.size());
        assertEquals("3", vehiclePositionList.get(0).getId());


    }
}