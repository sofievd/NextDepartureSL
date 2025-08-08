package se.iths.nextdeparturesl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DownloadTasker {

    private static final Logger log = LoggerFactory.getLogger(DownloadTasker.class);

    public void downloadStaticDataDaily() {

        ApiDownloader download = new ApiDownloader();
        FileUtil fileUtil = new FileUtil();

        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));
        String yesterday = now.minusDays(1).format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));
        log.info("creating directory for: {}", today);
        File directory = new File("src/main/resources/static/" + today);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (download.downloadGtfsStatic(directory)) {
            log.info("done downloading");
            log.info("trying to delete directory for: {}", yesterday);
            File zipFile = new File("src/main/resources/static/" + yesterday);
            fileUtil.deleteFile(zipFile);
            log.info("File deleted: {}", yesterday);
        }
    }
}
