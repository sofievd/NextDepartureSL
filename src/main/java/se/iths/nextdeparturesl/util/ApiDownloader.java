package se.iths.nextdeparturesl.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ApiDownloader {
    private static final Logger log = LoggerFactory.getLogger(ApiDownloader.class);
    private final String URL_GTFS_STATIC = "https://opendata.samtrafiken.se/gtfs/sl/sl.zip?key=";
    private String API_KEY_STATIC = System.getenv("API_KEY");
    private final String URL_GTFS_REAL_VEHICLE = "https://opendata.samtrafiken.se/gtfs-rt/sl/VehiclePositions.pb?key=";
    private final String API_KEY_REAL = System.getenv("API_KEY_REAL");

    FileUtil fileUtil = new FileUtil();

    public File downloadGtfsStatic() {
        File file = new File(getClass().getClassLoader().getResource("").getFile());
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));
        log.info("trying to download today's zip file: {}", today);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URL_GTFS_STATIC + API_KEY_STATIC);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode: {}", statusCode);
                if (statusCode == 200) {
                    log.info("trying to write into file: {}", file.getName());
                    FileOutputStream outputStream = new FileOutputStream((file.getPath() + "/" + today + "-sl.zip"));
                    fileUtil.writeToFile(outputStream, response.getEntity().getContent());
                    log.info("successfully wrote into file: {}", file.getName());
                    File newFile = new File(file.getPath()+"/"+today+"-sl.zip");
                    return newFile;
                } else {
                    log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                    return null;
                }
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadGtfsRealTimeVehiclePosition() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URL_GTFS_REAL_VEHICLE + API_KEY_REAL);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode: {}", statusCode);
                if (statusCode == 200) {
                    return response.getEntity().getContent().readAllBytes();
                } else {
                    log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new byte[0];
    }
}

