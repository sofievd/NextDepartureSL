package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MapCreatorTest {
    private MapCreator mapCreator;

    @BeforeEach
    void setUp() {
        File gtfsRootFolderPath = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        this.mapCreator = new MapCreator(gtfsRootFolderPath);

    }

    @Test
    void createStopTimeMapWithStopId() {
        Map<String, List<StopTime>> map = mapCreator.createStopTimeMapWithStopId();
        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
    }

    @Test
    void createTripMapWithTripId() {
        Map<String, Trip> map = mapCreator.createTripMapWithTripId();
        assertFalse(map.isEmpty());
        assertEquals(32, map.size());
    }

    @Test
    void createRouteMapWithRouteId() {
        Map<String, Route> map = mapCreator.createRouteMapWithRouteId();
        assertFalse(map.isEmpty());
        assertEquals(3, map.size());
    }

    @Test
    void createCalendarDateMapWithServiceId() {
        Map<String, List<CalendarDate>> map = mapCreator.createCalendarDateMapWithServiceId();
        assertFalse(map.isEmpty());
        assertEquals(11, map.size());
    }


    @Test
    void createStopIdMapWithStopName() {
        Map<String, List<String>> map = mapCreator.createStopIdMapWithStopName();
        assertFalse(map.isEmpty());
        assertEquals(6, map.size());
        assertEquals(map.get("Stavsnäs"), List.of("9022001000101001"));
    }

    @Test
    void createTripIdListMapWithServiceId() {
        Map<String, List<String>> map = mapCreator.createTripIdListMapWithServiceId();

        assertFalse(map.isEmpty());
        assertEquals(11, map.size());
    }

    @Test
    void getStopNameList() {
        List<String> stopNameList = mapCreator.getStopNameList();
        assertFalse(stopNameList.isEmpty());
        assertEquals(6, stopNameList.size());
    }
}