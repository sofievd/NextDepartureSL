package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.view.Departure;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {
    private SearchService searchService;

    private GtfsDataHolder gtfsDataHolder;

    @BeforeEach
    void setUp() {
        gtfsDataHolder = new GtfsDataHolder("src/test/resources/GTFS_SL_TEST/");
        searchService = new SearchService(gtfsDataHolder);
        searchService.setUp();
    }

    @Test
    void getStationList() {
        List<String> stations = searchService.getStationList();
        assertNotNull(stations);
        assertEquals(5, stations.size());
    }

    @Test
    void getStationIdWithName() {
        Set<String> stationIds = searchService.getStationIdWithName("Styrsvik");
        assertNotNull(stationIds);
        assertEquals(1, stationIds.size());

    }

    @Test
    void getStopTimesWithStationId() {
        Set<StopTime> stopTimes = searchService.getStopTimesWithStationId("9022001000101001");
        assertNotNull(stopTimes);
        assertEquals(31, stopTimes.size());
    }

    @Test
    void getTripWithTripId() {
        Set<Trip> trips = searchService.getTripWithTripId("14010000670499113");
        assertNotNull(trips);
        assertEquals(1, trips.size());
    }

    @Test
    void getServiceIdWithTripId() {
        Set<String> serviceIds = searchService.getServiceIdWithTripId("14010000670499113");
        assertNotNull(serviceIds);
        assertEquals(1, serviceIds.size());

    }

    @Test
    void isServiceIdActiveAtDate() {
        assertTrue(searchService.isServiceIdActiveAtDate("19", "20250202"));
    }

    @Test
    void getTripsWithServiceId() {
        Set<Trip> trips = searchService.getTripsWithServiceId("19");
        assertNotNull(trips);
        assertEquals(7, trips.size());

    }

    @Test
    void getStopTimeWithTrip() {
        Set<Trip> trips = searchService.getTripsWithServiceId("19");

        Map<String, List<StopTime>> map = searchService.makeMap(searchService.getStopTimesWithStationId("9022001000101001").stream().toList());
        Set<StopTime> stopTimes = searchService.getStopTimeWithTrip(trips.stream().toList(), map);
        assertNotNull(stopTimes);
        assertEquals(7, stopTimes.size());
    }

    @Test
    void getGetDeparturesFromStopNameWithStopTime() {
        Set<Trip> trips = searchService.getTripsWithServiceId("19");
        Map<String, List<StopTime>> map = searchService.makeMap(searchService.getStopTimesWithStationId("9022001000101001").stream().toList());
        Set<StopTime> stopTimes = searchService.getStopTimeWithTrip(trips.stream().toList(), map);

        List<Departure> departures = searchService.getDeparturesWithStopTimeToday("19:30:00", stopTimes.stream().toList(), "20250202");
        for (Departure departure : departures) {
            System.out.println(departure);
        }
        assertNotNull(departures);
        assertEquals(1, departures.size());



    }

    @Test
    void getDeparturesFromStopName() {
        List<Departure> departures = searchService.getDeparturesFromStopName("Stavsnäs", "20250202-19:30:00");
        assertNotNull(departures);
        for (Departure departure : departures) {
            System.out.println(departure);
        }
        assertEquals(1, departures.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", departures.get(0).getDestination());
        assertEquals("Water Transport", departures.get(0).getVehicleType());
    }
}