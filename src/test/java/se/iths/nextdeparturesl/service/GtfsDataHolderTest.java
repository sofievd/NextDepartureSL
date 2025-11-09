package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.dto.Station;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;
import se.iths.nextdeparturesl.util.GtfsFileHandler;
import se.iths.nextdeparturesl.util.MapCreator;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GtfsDataHolderTest {
    private GtfsDataHolder gtfsDataHolder;

    @BeforeEach
    void setUp() {
        File gtfsRootFolderPath = new File(getClass().getClassLoader().getResource("2025-08-19-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(gtfsRootFolderPath);
        MapCreator creator = new MapCreator();
        creator.setFileHandler(fileHandler);

        gtfsDataHolder = GtfsDataHolder.getInstance();
        gtfsDataHolder.setStationList(creator.getStopNameList());
        gtfsDataHolder.setStopIdToStopTimes(creator.createStopTimeMapWithStopId());
        gtfsDataHolder.setTripIdToTrips(creator.createTripMapWithTripId());
        gtfsDataHolder.setRouteIdToRoutes(creator.createRouteMapWithRouteId());
        gtfsDataHolder.setServiceIdToCalendarDates(creator.createCalendarDateMapWithServiceId());
        gtfsDataHolder.setStopNameToStopId(creator.createStopIdMapWithStopName());
        gtfsDataHolder.setServiceIdToTripId(creator.createTripIdListMapWithServiceId());
        gtfsDataHolder.setParentStationIdToStops(creator.createParentStationIdToStops());
        gtfsDataHolder.setTripIdToStopTimes(creator.createTripIdToStopTimes());
    }

    @AfterEach
    void tearDown() {
        gtfsDataHolder.setStationList(Collections.emptyList());
        gtfsDataHolder.setRouteIdToRoutes(Collections.emptyMap());
        gtfsDataHolder.setTripIdToTrips(Collections.emptyMap());
        gtfsDataHolder.setServiceIdToCalendarDates(Collections.emptyMap());
        gtfsDataHolder.setServiceIdToTripId(Collections.emptyMap());
        gtfsDataHolder.setStopIdToStopTimes(Collections.emptyMap());
        gtfsDataHolder.setStopNameToStopId(Collections.emptyMap());
        gtfsDataHolder.setTripIdToStopTimes(Collections.emptyMap());
    }

    @Test
    void getStationList() {
        List<Station> stations = gtfsDataHolder.getStationList();
        assertNotNull(stations);
        assertFalse(stations.isEmpty());
        assertEquals(2, stations.size());

    }

    @Test
    void getStopIdToStopTimes() {
        Map<String, List<StopTime>> map = gtfsDataHolder.getStopIdToStopTimes();
        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
        assertNotNull(map);
    }

    @Test
    void getStopNameToStopId() {
        Map<String, List<String>> map = gtfsDataHolder.getStopNameToStopId();
        assertFalse(map.isEmpty());
        assertEquals(6, map.size());
        assertEquals(map.get("Stavsnäs"), List.of("9022001000101001"));
        assertNotNull(map);
    }

    @Test
    void getServiceIdToTripId() {
        Map<String, List<String>> map = gtfsDataHolder.getServiceIdToTripId();
        assertFalse(map.isEmpty());
        assertEquals(11, map.size());
        assertNotNull(map);
    }

    @Test
    void getCalendarDates() {
        Map<String, List<CalendarDate>> map = gtfsDataHolder.getServiceIdToCalendarDates();
        assertFalse(map.isEmpty());
        assertEquals(11, map.size());
        assertNotNull(map);
    }

    @Test
    void getRoutes() {
        Map<String, Route> map = gtfsDataHolder.getRouteIdToRoutes();
        assertFalse(map.isEmpty());
        assertEquals(3, map.size());
        assertNotNull(map);
    }

    @Test
    void getTripIdToTrips() {
        Map<String, Trip> map = gtfsDataHolder.getTripIdToTrips();
        assertFalse(map.isEmpty());
        assertEquals(32, map.size());
        assertNotNull(map);
        assertNotNull(map);
    }

    @Test
    void getTripIdToStopTimes() {
        Map<String, List<StopTime>> map = gtfsDataHolder.getTripIdToStopTimes();
        assertFalse(map.isEmpty());
        assertEquals(32, map.size());
        assertNotNull(map);
    }
}