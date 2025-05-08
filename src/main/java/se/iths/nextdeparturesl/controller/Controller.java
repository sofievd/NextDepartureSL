package se.iths.nextdeparturesl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.iths.nextdeparturesl.service.SearchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class Controller {
    private static final Logger log = LogManager.getLogger(Controller.class);
    private final SearchService searchService = new SearchService();
    public Controller(){
searchService.setUp();
    }

    @GetMapping("/stationList")
    public ResponseEntity<?> stationList() {
       log.info("getting station list");
        return ResponseEntity.ok().body(searchService.getStationList());
    }

    @GetMapping("/searchStation/Barkarby")
    public ResponseEntity<?> searchStation(@RequestParam String stationName) {
        log.info("searching station: {}", stationName);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok().body(searchService.getDeparturesFromStopName(stationName,now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"))));
    }
}
