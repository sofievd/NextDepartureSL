package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.VehiclePosition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GtfsVehiclePositionHandlerTest {
    private File file = new File(getClass().getClassLoader().getResource("realtime/vehiclePositions.pb").getFile());
    private GtfsVehiclePositionHandler handler = new GtfsVehiclePositionHandler();


    @Test
    void readVehiclePositions() {
        List<VehiclePosition> vehicleList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(file);){
            vehicleList = handler.readVehiclePositions(fileInputStream.readAllBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        assertNotNull(vehicleList);
        assertFalse(vehicleList.isEmpty());
    }

}