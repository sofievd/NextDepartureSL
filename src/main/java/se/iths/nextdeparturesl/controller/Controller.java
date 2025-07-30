package se.iths.nextdeparturesl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import se.iths.nextdeparturesl.service.SearchService;
import se.iths.nextdeparturesl.util.ApiDownload;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
public class Controller {
    private static final Logger log = LogManager.getLogger();
    private final SearchService searchService = new SearchService();
    public Controller(){
searchService.setUp();
    }

    @GetMapping("/stationList")
    public ResponseEntity<?> stationList() {
       log.info("getting station list");
        return ResponseEntity.ok().body(searchService.getStationList());
    }
    @GetMapping("/download")
    public ResponseEntity<?> download() {
        System.out.println("here");
        ApiDownload download = new ApiDownload();
        download.download();
        return ResponseEntity.ok().body("download completed");

    }

    @GetMapping("/searchStation")
    public ResponseEntity<?> searchStation(@RequestParam String stationName) {
        log.info("searching station: {}", stationName);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok().body(searchService.getDeparturesFromStopName(stationName,now));
    }
}
