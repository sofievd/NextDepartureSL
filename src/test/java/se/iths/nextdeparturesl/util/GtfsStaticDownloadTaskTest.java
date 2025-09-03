package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.Test;

class GtfsStaticDownloadTaskTest {
    GtfsStaticDownloadTask tasker = new GtfsStaticDownloadTask();


    @Test
    void downloadStaticDataDaily() {
        tasker.run();
    }
}