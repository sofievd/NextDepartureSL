package se.iths.nextdeparturesl.util;

import se.iths.nextdeparturesl.service.GtfsVehiclePositionHolder;

import java.util.TimerTask;

public class GtfsRealtimeDownloadTask extends TimerTask {
    private ApiDownloader apiDownloader = new ApiDownloader();
    private GtfsVehiclePositionHolder vehiclePositionHolder;

    @Override
    public void run() {
        byte[] vehiclePositions = apiDownloader.downloadGtfsRealTimeVehiclePosition();
        vehiclePositionHolder = GtfsVehiclePositionHolder.getInstance();
        vehiclePositionHolder.setVehiclePositionBytes(vehiclePositions);

    }

}
