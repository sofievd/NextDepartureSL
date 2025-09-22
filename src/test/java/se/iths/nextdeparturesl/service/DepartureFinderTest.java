package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.DTO.Departure;
import se.iths.nextdeparturesl.util.GtfsFileHandler;
import se.iths.nextdeparturesl.util.MapCreator;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DepartureFinderTest {
    private DepartureFinder departureFinder;

    @BeforeEach
    void setUp() {
        String path = getClass().getClassLoader().getResource("2025-08-18-sl.zip").getPath();
        GtfsFileHandler fileHandler = new GtfsFileHandler(new File(path));
        MapCreator creator = new MapCreator();
        creator.setFileHandler(fileHandler);

        GtfsDataHolder dataHolder = GtfsDataHolder.getInstance();
        dataHolder.setStationList(creator.getStopNameList());
        dataHolder.setStopIdToStopTimes(creator.createStopTimeMapWithStopId());
        dataHolder.setTripIdToTrips(creator.createTripMapWithTripId());
        dataHolder.setRouteIdToRoutes(creator.createRouteMapWithRouteId());
        dataHolder.setServiceIdToCalendarDates(creator.createCalendarDateMapWithServiceId());
        dataHolder.setStopNameToStopId(creator.createStopIdMapWithStopName());
        dataHolder.setServiceIdToTripId(creator.createTripIdListMapWithServiceId());

        departureFinder = new DepartureFinder();
        departureFinder.setGtfsDataHolder(dataHolder);
        departureFinder.setMaps();
    }

    @Test
    void getStationList() {
        List<String> stations = departureFinder.getStationList();
        assertNotNull(stations);
        assertEquals(6, stations.size());
    }

    @Test
    void getStationIdWithName() {
        Set<String> stationIds = departureFinder.getStationIdWithName("Styrsvik");
        assertNotNull(stationIds);
        assertEquals(1, stationIds.size());

    }

    @Test
    void getStopTimesWithStationId() {
        Set<StopTime> stopTimes = departureFinder.getStopTimesWithStationId("9022001000101001");
        assertNotNull(stopTimes);
        assertEquals(31, stopTimes.size());
    }

    @Test
    void getTripWithTripId() {
        Set<Trip> trips = departureFinder.getTripWithTripId("14010000670499113");
        assertNotNull(trips);
        assertEquals(1, trips.size());
    }

    @Test
    void getServiceIdWithTripId() {
        Set<String> serviceIds = departureFinder.getServiceIdWithTripId("14010000670499113");
        assertNotNull(serviceIds);
        assertEquals(1, serviceIds.size());

    }

    @Test
    void isServiceIdActiveAtDate() {
        assertTrue(departureFinder.isServiceIdActiveAtDate("19", "20250202"));
    }

    @Test
    void getTripsWithServiceId() {
        Set<Trip> trips = departureFinder.getTripsWithServiceId("19");
        assertNotNull(trips);
        assertEquals(7, trips.size());
    }

    @Test
    void getStopTimeWithTrip() {
        Set<Trip> trips = departureFinder.getTripsWithServiceId("19");

        Map<String, List<StopTime>> map = departureFinder.createTripIdToStopTimeMap(departureFinder.getStopTimesWithStationId("9022001000101001").stream().toList());
        Set<StopTime> stopTimes = departureFinder.getStopTimeWithTrip(trips.stream().toList(), map);
        assertNotNull(stopTimes);
        assertEquals(7, stopTimes.size());
    }

    @Test
    void getDeparturesFromStopName() {
        List<Departure> departures = departureFinder.getDeparturesFromStopName("Stavsnäs",
                LocalDateTime.of(2025,2,2,19,30,0));
        assertNotNull(departures);
        for (Departure departure : departures) {
            System.out.println(departure);
        }
        assertEquals(1, departures.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", departures.get(0).getDestination());
        assertEquals("Water Transport", departures.get(0).getVehicleType());
    }

}