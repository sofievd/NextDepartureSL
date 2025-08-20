package se.iths.nextdeparturesl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.nextdeparturesl.service.GtfsDataHolder;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

//TODO: have a method that will download realtime data every 15 seconds
public class DownloadTasker extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(DownloadTasker.class);
    private ApiDownloader download = new ApiDownloader();
    private FileUtil fileUtil = new FileUtil();
    private GtfsDataHolder gtfsDataHolder;


    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));
        String yesterday = now.minusDays(1).format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));

        File directory = new File(getClass().getClassLoader().getResource("").getFile());
        System.out.println(directory.getPath());
        log.info("Trying to download new data for: {}", today);
        if (download.downloadGtfsStatic(directory, yesterday)) {
            log.info("done downloading");

            log.info("trying to delete file for: {}", yesterday);
            System.out.println(yesterday);
            File oldFile = new File(getClass().getClassLoader().getResource(yesterday + "-sl.zip").getFile());
            fileUtil.deleteFile(oldFile);
            log.info("File deleted: {}", yesterday);

            File newFile = new File(getClass().getClassLoader().getResource(today + "-sl.zip").getFile());
            log.info("updating files for searching");
            gtfsDataHolder = GtfsDataHolder.getInstance();
            gtfsDataHolder.updateFile(newFile);
            log.info("Creating new maps");
            gtfsDataHolder.createMaps();
        }

    }
}
