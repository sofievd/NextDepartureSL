package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class ApiDownloaderTest {
    private ApiDownloader apiDownloader;

    @BeforeEach
    void setUp() {
        apiDownloader = new ApiDownloader();

    }

    @Test
    void downloadGtfsStatic() {
        File file = apiDownloader.downloadGtfsStatic();
        assertNull(file);
    }

    @Test
    void testDownloadGtfsRealTimeVehiclePosition() {
        byte[] bytes = apiDownloader.downloadGtfsRealTimeVehiclePosition();
        assertNull(bytes);
    }
}