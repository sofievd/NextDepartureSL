package se.iths.nextdeparturesl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.nextdeparturesl.service.GtfsDataHolder;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class GtfsStaticDownloadTask extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(GtfsStaticDownloadTask.class);

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ofPattern(("yyyy-MM-dd")));

        ApiDownloader download = new ApiDownloader();
        log.info("Trying to download new data for: {}", today);
        File newFile = download.downloadGtfsStatic();

        if (newFile != null) {
            log.info("done downloading");
            GtfsFileHandler fileHandler = new GtfsFileHandler(newFile);
            MapCreator creator = new MapCreator();
            creator.setFileHandler(fileHandler);

            log.info("updating files for searching");
            GtfsDataHolder gtfsDataHolder = GtfsDataHolder.getInstance();
            log.info("Creating new maps");
            gtfsDataHolder.setStationList(creator.getStopNameList());
            gtfsDataHolder.setStopIdToStopTimes(creator.createStopTimeMapWithStopId());
            gtfsDataHolder.setTripIdToTrips(creator.createTripMapWithTripId());
            gtfsDataHolder.setRouteIdToRoutes(creator.createRouteMapWithRouteId());
            gtfsDataHolder.setServiceIdToCalendarDates(creator.createCalendarDateMapWithServiceId());
            gtfsDataHolder.setStopNameToStopId(creator.createStopIdMapWithStopName());
            gtfsDataHolder.setServiceIdToTripId(creator.createTripIdListMapWithServiceId());
            gtfsDataHolder.setParentStationIdToStops(creator.createParentStationIdToStops());
            gtfsDataHolder.setTripIdToStopTimes(creator.createTripIdToStopTimes());
            gtfsDataHolder.setStopIdToStopName(creator.createStopNameWithStopId());
            log.info("done creating maps");

            newFile.delete();
        }

    }
}
