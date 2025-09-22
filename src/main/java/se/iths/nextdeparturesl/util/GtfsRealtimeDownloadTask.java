package se.iths.nextdeparturesl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.nextdeparturesl.model.VehiclePosition;
import se.iths.nextdeparturesl.service.GtfsVehiclePositionHolder;

import java.util.List;
import java.util.TimerTask;

public class GtfsRealtimeDownloadTask extends TimerTask {
    private static final Logger log = LoggerFactory.getLogger(GtfsRealtimeDownloadTask.class);


    @Override
    public void run() {
        log.info("GtfsRealtimeDownloadTask started");
        ApiDownloader apiDownloader = new ApiDownloader();
        byte[] vehiclePositionsBytes = apiDownloader.downloadGtfsRealTimeVehiclePosition();

        log.info("data downloaded");
        GtfsVehiclePositionHandler vehiclePositionHandler = new GtfsVehiclePositionHandler();
        List<VehiclePosition> vehiclePositions = vehiclePositionHandler.readVehiclePositions(vehiclePositionsBytes);

        GtfsVehiclePositionHolder vehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();
        vehiclePositionHolder.setVehiclePositions(vehiclePositions);
        log.info("GtfsRealtimeDownloadTask completed");
    }
}
