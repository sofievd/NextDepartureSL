package se.iths.nextdeparturesl.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiDownloader {
    private static final Logger log = LogManager.getLogger();
    private final String URL_GTFS_STATIC = "https://opendata.samtrafiken.se/gtfs/sl/sl.zip?key=";


    private String API_KEY_STATIC = System.getenv("API_KEY");
    private final String URL_GTFS_REAL_VEHICLE = "https://opendata.samtrafiken.se/gtfs-rt/sl/VehiclePositions.pb?key=";

    private String API_KEY_REAL = System.getenv("API_KEY_REAL");

    FileUtil fileUtil = new FileUtil();

    public File downloadGtfsStatic() {
        File file;
        try {
            file = File.createTempFile("sl", ".zip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (API_KEY_STATIC != null || !API_KEY_STATIC.equals("")) {
            LocalDateTime now = LocalDateTime.now();
            String today = now.format(DateTimeFormatter.ofPattern(("yyyy-MM-dd")));
            log.info("trying to download today's zip file: {}", today);

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(URL_GTFS_STATIC + API_KEY_STATIC);
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    log.info("statusCode: {}", statusCode);

                    if (statusCode == 200) {
                        log.info("trying to write into file: {}", file.getName());
                        FileOutputStream outputStream = new FileOutputStream(file);
                        fileUtil.writeToFile(outputStream, response.getEntity().getContent());
                        log.info("successfully wrote into file: {}", file.getName());
                        return file;
                    } else {
                        log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                        return null;
                    }
                }
            } catch (IOException e) {
                log.warn(e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            log.warn("API key not available; API_KEY={}", API_KEY_STATIC);
            return null;
        }
    }

    public byte[] downloadGtfsRealTimeVehiclePosition() {
        if (API_KEY_REAL != null || !API_KEY_REAL.equals("")) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(URL_GTFS_REAL_VEHICLE + API_KEY_REAL);
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    log.info("statusCode: {}", statusCode);
                    if (statusCode == 200) {
                        return response.getEntity().getContent().readAllBytes();
                    } else {
                        log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                        return new byte[0];
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("API key not available; API_KEY={}", API_KEY_REAL);
            return new byte[0];
        }
    }
}

