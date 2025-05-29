package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GtfsDataHolderTest {
    private GtfsDataHolder gtfsDataHolder;

    @BeforeEach
    void setUp() {
        String gtfsRootFolderPath = getClass().getClassLoader().getResource("GTFS_SL_TEST/").getPath();
        gtfsDataHolder = new GtfsDataHolder(gtfsRootFolderPath);
    }

    @Test
    void createStopTimeMapWithStopId_notEmpty_shouldReturnStopTimes() {
        Map<String, List<StopTime>> map = gtfsDataHolder.createStopTimeMapWithStopId();

        assertFalse(map.isEmpty());

        assertEquals(2, map.size());
    }

    @Test
    void createTripMapWithTripId() {
        Map<String, Trip> map = gtfsDataHolder.createTripMapWithTripId();
        assertFalse(map.isEmpty());
        assertEquals(31, map.size());
    }

    @Test
    void createRouteMapWithRouteId() {
        Map<String, Route> map = gtfsDataHolder.createRouteMapWithRouteId();
        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
    }

    @Test
    void createCalendarDateMapWithServiceId() {
        Map<String, List<CalendarDate>> map = gtfsDataHolder.createCalendarDateMapWithServiceId();
        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void createStopIdMapWithStopName() {
        Map<String, List<String>> map = gtfsDataHolder.createStopIdMapWithStopName();
        assertFalse(map.isEmpty());
        assertEquals(5, map.size());
        assertEquals(map.get("Stavsnäs"), List.of("9022001000101001"));
    }

    @Test
    void createTripIdListMapWithServiceId() {
        Map<String, List<String>> map = gtfsDataHolder.createTripIdListMapWithServiceId();

        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void getStationList() {
        assertNull(gtfsDataHolder.getStationList());
    }

    @Test
    void getStopIdTostopTimes() {
        assertNull(gtfsDataHolder.getStopIdToStopTimes());
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
        assertNull(gtfsDataHolder.getServiceIdToCalendarDates());
    }

    @Test
    void getRoutes() {
        assertNull(gtfsDataHolder.getRouteIdToRoutes());
    }

    @Test
    void getTripIdTotrips() {
        assertNull(gtfsDataHolder.getTripIdToTrips());
    }
}