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
    private final DepartureFinder departureFinder = new DepartureFinder();
    private final VehiclePositionLoader vehiclePositionLoader = new VehiclePositionLoader();

    public Controller() {
        departureFinder.setUp();
        departureFinder.startUpdate();
        vehiclePositionLoader.startUpdate();
    }

    @GetMapping("/api/stationList")
    public ResponseEntity<?> stationList() {
        log.info("getting station list");
        return ResponseEntity.ok().body(departureFinder.getStationList());
    }


    @GetMapping("/api/departure")
    public ResponseEntity<?> searchStation(@RequestParam String id) {
        log.info("searching station: {}", id);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok().body(departureFinder.getDeparturesFromStopId(id, now));
    }

    @GetMapping("/api/vehiclePositions")
    public ResponseEntity<?> vehiclePosition() {
        log.info("getting vehicle position");
        return ResponseEntity.ok().body(vehiclePositionLoader.getVehiclePositions());
    }
}
