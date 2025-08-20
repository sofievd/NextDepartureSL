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
import java.util.Date;

//TODO: create a method that will download the realtime data

public class ApiDownloader {
    private static final Logger log = LoggerFactory.getLogger(ApiDownloader.class);
    private final String URL_GTFS_STATIC = "https://opendata.samtrafiken.se/gtfs/sl/sl.zip?key=";
    private String API_KEY_STATIC = System.getenv("API_KEY");
    private final String URL_GTFS_REAL_VEHICLE = "https://opendata.samtrafiken.se/gtfs-rt/sl/VehiclePositions.pb?key=";
    private final String API_KEY_REAL = System.getenv("API_KEY_REAL");

    FileUtil fileUtil = new FileUtil();

    public boolean downloadGtfsStatic(File file,String date) {
        log.info("trying to download today's zip file: " + new Date());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URL_GTFS_STATIC + API_KEY_STATIC);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode: {}", statusCode);
                if (statusCode == 200) {
                    log.info("trying to write into file: {}", file.getName());
                    FileOutputStream outputStream = new FileOutputStream((file.getPath() +"/"+ date+"-sl.zip"));
                    fileUtil.writeToFile(outputStream, response.getEntity().getContent());
                    log.info("successfully wrote into file: {}", file.getName());
                } else {
                    log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public byte[] downloadGtfsRealTimeVehiclePosition() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URL_GTFS_REAL_VEHICLE + API_KEY_REAL);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode: {}", statusCode);
                if (statusCode == 200) {
                    //fileUtil.clearFileContent(file);
                    //log.info("trying to write into file: {}", file.getName());
                    //FileOutputStream outputStream = new FileOutputStream(file.getPath());
                    //fileUtil.writeToFile(outputStream, response.getEntity().getContent());
                    return response.getEntity().getContent().readAllBytes();
                }else{
                    log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new byte[0];
    }
}

