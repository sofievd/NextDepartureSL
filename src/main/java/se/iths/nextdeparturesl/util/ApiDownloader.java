package se.iths.nextdeparturesl.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

//TODO: create a method that will download the realtime data

public class ApiDownloader {
    private static final Logger log = LoggerFactory.getLogger(ApiDownloader.class);
    private final String URLString = "https://opendata.samtrafiken.se/gtfs/sl/sl.zip?key=";
    private String APIKEY = System.getenv("API_KEY");
    FileUtil fileUtil = new FileUtil();

    public boolean downloadGtfsStatic(File file) {
        log.info("trying to download today's zip file: " + new Date());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URLString + APIKEY);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode: {}", statusCode);
                if(statusCode == 200) {
                    log.info("trying to write into file: {}", file.getName());
                    FileOutputStream outputStream = new FileOutputStream(file.getPath()+"/sl.zip");
                    fileUtil.writeToFile(outputStream, response.getEntity().getContent());
                 log.info("successfully wrote into file: {}", file.getName());
                } else {
                    log.warn("error downloading file, HTTP Status code: {} ", statusCode);
                    return false;
                }
            }
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
        return true;
   }

}

