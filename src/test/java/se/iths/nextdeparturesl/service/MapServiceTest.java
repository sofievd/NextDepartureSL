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
    private GtfsDataHolder gtfsDataHolder;

    @BeforeEach
    void setUp() {
        gtfsDataHolder = new GtfsDataHolder("src/test/resources/GTFS_SL_TEST/");
    }

    @Test
    void createStopTimeMapWithStopId_notEmpty_shouldReturnStopTimes() {
        Map<BigInteger, List<StopTime>> map = gtfsDataHolder.createStopTimeMapWithStopId("stop_times.txt");

        assertFalse(map.isEmpty());

        assertEquals(2, map.size());
    }

    @Test
    void createTripMapWithTripId() {
        Map<BigInteger, Trip> map = gtfsDataHolder.createTripMapWithTripId("GTFS_SL_TEST/trips.txt");
        assertFalse(map.isEmpty());
        assertEquals(31, map.size());
    }

    @Test
    void createRouteMapWithRouteId() {
        Map<String, Route> map = gtfsDataHolder.createRouteMapWithRouteId("GTFS_SL_TEST/routes.txt");
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
    }

    @Test
    void createCalendarDateMapWithServiceId() {
        Map<BigInteger, List<CalendarDate>> map = gtfsDataHolder.createCalendarDateMapWithServiceId("calendar_dates.txt");
        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void createStopIdMapWithStopName() {
        Map<String, List<BigInteger>> map = gtfsDataHolder.createStopIdMapWithStopName("stops.txt");
        assertFalse(map.isEmpty());
        assertEquals(5, map.size());
        assertEquals(map.get("Stavsnäs"), List.of(new BigInteger("9022001000101001")));
    }

    @Test
    void createTripIdListMapWithServiceId() {
        Map<BigInteger, List<BigInteger>> map = gtfsDataHolder.createTripIdListMapWithServiceId("trips.txt");

        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void getStationList() {
        assertNull(gtfsDataHolder.getStationList());
    }

    @Test
    void getStopIdTostopTimes() {
        assertNull(gtfsDataHolder.getStopIdTostopTimes());
    }

    @Test
    void getStopNameToStopId() {
        assertNull(gtfsDataHolder.getStopNameToStopId());
    }

    @Test
    void getServiceIdToTripId() {
        assertNull(gtfsDataHolder.getServiceIdToTripId());
    }

    @Test
    void getCalendarDates() {
        assertNull(gtfsDataHolder.getCalendarDates());
    }

    @Test
    void getRoutes() {
        assertNull(gtfsDataHolder.getRoutes());
    }

    @Test
    void getTripIdTotrips() {
        assertNull(gtfsDataHolder.getTripIdTotrips());
    }
}