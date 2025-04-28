package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.view.Departure;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchServiceTest {
    private SearchService searchService;

    private MapService mapService;
    private Map<BigInteger, List<StopTime>> StopIdTostopTimes;
    private Map<BigInteger, Trip> TripIdTotrips;
    private Map<String, Route> routes;
    private Map<BigInteger, List<CalendarDate>> calendarDates;
    private Map<String, List<BigInteger>> stopNameToStopId;
    private Map<BigInteger, List<BigInteger>> serviceIdToTripId;

    @BeforeEach
    void setUp() {
        mapService = new MapService();
        searchService = new SearchService(mapService);
        searchService.testSetUp();
    }

    @Test
    void getStationList() {
        List<String> stations = searchService.getStationList();
        assertNotNull(stations);
        assertEquals(0, stations.size());
    }

    @Test
    void getStationIdWithName() {
        Set<BigInteger> stationIds = searchService.getStationIdWithName("Styrsvik");
        assertNotNull(stationIds);
        assertEquals(1, stationIds.size());

    }

    @Test
    void getStopTimesWithStationId() {
        Set<StopTime> stopTimes = searchService.getStopTimesWithStationId(new BigInteger("9022001000101001"));
        assertNotNull(stopTimes);
        assertEquals(31, stopTimes.size());
    }

    @Test
    void getTripWithTripId() {
        Set<Trip> trips = searchService.getTripWithTripId(new BigInteger("14010000670499113"));
        assertNotNull(trips);
        assertEquals(1, trips.size());
    }

    @Test
    void getServiceIdWithTripId() {
        Set<BigInteger> serviceIds = searchService.getServiceIdWithTripId(new BigInteger("14010000670499113"));
        assertNotNull(serviceIds);
        assertEquals(1, serviceIds.size());

    }

    @Test
    void getTodayServiceIdWithServiceId() {
        Set<BigInteger> serviceIds = searchService.getTodayServiceIdWithServiceId(new BigInteger("19"), "20250202");
        assertNotNull(serviceIds);
        assertEquals(1, serviceIds.size());
    }

    @Test
    void getTripsWithServiceId() {
        Set<Trip> trips = searchService.getTripsWithServiceId(new BigInteger("19"));
        assertNotNull(trips);
        assertEquals(7, trips.size());

    }

    @Test
    void getStopTimeWithTrip() {
        Set<Trip> trips = searchService.getTripsWithServiceId(new BigInteger("19"));

        Map<String, List<StopTime>> map = searchService.makeMap(searchService.getStopTimesWithStationId(new BigInteger("9022001000101001")).stream().toList());
        Set<StopTime> stopTimes = searchService.getStopTimeWithTrip(trips.stream().toList(), map);
        assertNotNull(stopTimes);
        assertEquals(7, stopTimes.size());
    }

    @Test
    void getGetDeparturesFromStopNameWithStopTime() {
        Set<Trip> trips = searchService.getTripsWithServiceId(new BigInteger("19"));
        Map<String, List<StopTime>> map = searchService.makeMap(searchService.getStopTimesWithStationId(new BigInteger("9022001000101001")).stream().toList());
        Set<StopTime> stopTimes = searchService.getStopTimeWithTrip(trips.stream().toList(), map);

        List<Departure> departures = searchService.getDeparturesWithStopTime("19:30:00", stopTimes.stream().toList());
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