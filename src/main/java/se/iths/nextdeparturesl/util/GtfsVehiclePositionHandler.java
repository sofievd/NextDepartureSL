package se.iths.nextdeparturesl.util;

import com.google.transit.realtime.GtfsRealtime;
import se.iths.nextdeparturesl.view.VehiclePosition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GtfsVehiclePositionHandler {
    private File file;

    public GtfsVehiclePositionHandler(){}

    public GtfsVehiclePositionHandler(File file){
        this.file = file;
    }

    public List<VehiclePosition> readVehiclePositions() {
        List<VehiclePosition> vehicles = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            GtfsRealtime.FeedMessage message = GtfsRealtime.FeedMessage.parseFrom(fileInputStream);
            for (GtfsRealtime.FeedEntity entity : message.getEntityList()) {
                if (entity.hasVehicle()) {
                    VehiclePosition vehicle = new VehiclePosition(
                            entity.getVehicle().getTrip().getTripId(),
                            entity.getVehicle().getPosition().getLatitude(),
                            entity.getVehicle().getPosition().getLongitude(),
                            entity.getVehicle().getPosition().getBearing(),
                            entity.getVehicle().getPosition().getSpeed()
                    );
                    vehicles.add(vehicle);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return vehicles;
    }

}
