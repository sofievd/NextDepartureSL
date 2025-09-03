package se.iths.nextdeparturesl.util;

import com.google.transit.realtime.GtfsRealtime;
import se.iths.nextdeparturesl.model.VehiclePosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GtfsVehiclePositionHandler {

    public List<VehiclePosition> readVehiclePositions(byte[] input) {
        List<VehiclePosition> vehicles = new ArrayList<>();
        try {
            //  FileInputStream fileInputStream = new FileInputStream(file);
            GtfsRealtime.FeedMessage message = GtfsRealtime.FeedMessage.parseFrom(input);
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
