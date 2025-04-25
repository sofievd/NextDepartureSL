package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapServiceTest {
    private MapService mapService;
    @BeforeEach
    void setUp() {
        mapService = new MapService();
    }

    @Test
    void createStopTimeMapWithStopId() {
        Map<BigInteger, List<StopTime>> map = mapService.createStopTimeMapWithStopId("src/test/resources/GTFS_SL_TEST/stop-times.txt");

        assertFalse(map.isEmpty());

        assertEquals(2, map.size());
    }

    @Test
    void createTripMapWithTripId() {
        Map<BigInteger, Trip> map = mapService.createTripMapWithTripId("src/test/resources/GTFS_SL_TEST/trips.txt");
        assertFalse(map.isEmpty());
        assertEquals(31, map.size());
    }

    @Test
    void createRouteMapWithRouteId() {
        Map<String, Route> map = mapService.createRouteMapWithRouteId("src/test/resources/GTFS_SL_TEST/routes.txt");
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
    }

    @Test
    void createCalendarDateMapWithServiceId() {
        Map<BigInteger, List<CalendarDate>> map = mapService.createCalendarDateMapWithServiceId("src/test/resources/GTFS_SL_TEST/calendar_dates.txt");
        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void createStopIdMapWithStopName() {
        Map<String, List<BigInteger>> map = mapService.createStopIdMapWithStopName("src/test/resources/GTFS_SL_TEST/stops.txt");
        assertFalse(map.isEmpty());
        assertEquals(6, map.size());
    }

    @Test
    void createTripIdListMapWithServiceId() {
        Map<BigInteger, List<BigInteger>> map = mapService.createTripIdListMapWithServiceId("src/test/resources/GTFS_SL_TEST/trips.txt");

        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void getStationList() {
        assertNull(mapService.getStationList());
    }

    @Test
    void getStopIdTostopTimes() {
        assertNull(mapService.getStopIdTostopTimes());
    }

    @Test
    void getStopNameToStopId() {
        assertNull(mapService.getStopNameToStopId());
    }

    @Test
    void getServiceIdToTripId() {
        assertNull(mapService.getServiceIdToTripId());
    }

    @Test
    void getCalendarDates() {
        assertNull(mapService.getCalendarDates());
    }

    @Test
    void getRoutes() {
        assertNull(mapService.getRoutes());
    }

    @Test
    void getTripIdTotrips() {
        assertNull(mapService.getTripIdTotrips());
    }
}