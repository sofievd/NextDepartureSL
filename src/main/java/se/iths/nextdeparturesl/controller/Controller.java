package se.iths.nextdeparturesl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.iths.nextdeparturesl.service.SearchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class Controller {
    private final SearchService searchService = new SearchService();
    public Controller(){
searchService.setUp();
    }

    @GetMapping("/stationList")
    public ResponseEntity<?> stationList() {
        System.out.println("hämtar lista");
        return ResponseEntity.ok().body(searchService.getStationList());
    }

    @GetMapping("/searchStation/Barkarby")
    public ResponseEntity<?> searchStation(@RequestParam String stationName) {
        System.out.println("söker station: " + stationName);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok().body(searchService.getDeparturesFromStopName(stationName,now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"))));
    }
}
