package se.iths.nextdeparturesl.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
 //TODO: Static data updates every day
//TODO: realtime data updates every 15 seconds
public class ApiDownload {
    private final String URLString = "https://opendata.samtrafiken.se/gtfs/sl/sl.zip?key=";

    @Value("${api.key}")
    private String APIKEY;

    public void download() {

        try {
            URL obj = new URL(URLString + APIKEY);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(con.getInputStream()))) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {

                }
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer resp = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    resp.append(inputLine);
                }
                in.close();
                System.out.println(resp.toString());
            } else {
                System.out.println("Request failed");
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

