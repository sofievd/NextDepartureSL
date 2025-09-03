package se.iths.nextdeparturesl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.iths.nextdeparturesl.service.DepartureFinder;
import se.iths.nextdeparturesl.service.VehiclePositionLoader;

import java.time.LocalDateTime;


@CrossOrigin("*")
@RestController
public class Controller {
    private static final Logger log = LogManager.getLogger();
    private DepartureFinder departureFinder = new DepartureFinder();
    private VehiclePositionLoader vehiclePositionLoader = new VehiclePositionLoader();

    public Controller() {
        departureFinder.setUp();
        departureFinder.startUpdate();
        vehiclePositionLoader.startUpdate();
    }

    @GetMapping("/stationList")
    public ResponseEntity<?> stationList() {
        log.info("getting station list");
        return ResponseEntity.ok().body(departureFinder.getStationList());
    }

//    @GetMapping("/download")
//    public ResponseEntity<?> download() {
//        System.out.println("here");
//        ApiDownloader download = new ApiDownloader();
//        LocalDateTime now = LocalDateTime.now();
//        String today = now.format(DateTimeFormatter.ofPattern(("YYYY-MM-dd")));
//      //  download.download(today);
//        return ResponseEntity.ok().body("download completed");
//    }

    @GetMapping("/searchStation")
    public ResponseEntity<?> searchStation(@RequestParam String stationName) {
        log.info("searching station: {}", stationName);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok().body(departureFinder.getDeparturesFromStopName(stationName, now));
    }

    @GetMapping("/vehiclePositions")
    public ResponseEntity<?> vehiclePosition() {
        log.info("getting vehicle position");
        return ResponseEntity.ok().body(vehiclePositionLoader.getVehiclePositions());
    }
}
