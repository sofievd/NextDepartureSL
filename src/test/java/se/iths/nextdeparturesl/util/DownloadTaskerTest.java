package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DownloadTaskerTest {
    DownloadTasker tasker = new DownloadTasker();


    @Test
    void downloadStaticDataDaily() {
        tasker.downloadStaticDataDaily();
    }
}