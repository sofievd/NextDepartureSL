package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.*;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    private FileUtil fileUtil;

    @BeforeEach
    void setUp() {
        fileUtil = new FileUtil();
    }

    @Test
    void getStationList() {
        fileUtil.getStationList();
        assertFalse(fileUtil.getStationList().isEmpty());
    }

    @Test
    void getStopNameList() {
        Set<String> stopnameList = fileUtil.getStopNameList("src/test/resources/GTFS_SL_TEST/stops.txt");
        assertEquals(6, stopnameList.size());
        assertTrue(stopnameList.contains("idöborg"));
    }

    @Test
    void createStopTimeMapWithStopId() {
        Map<BigInteger, List<StopTime>> map = fileUtil.createStopTimeMapWithStopId("src/test/resources/GTFS_SL_TEST/stop-times.txt");

        assertFalse(map.isEmpty());

        assertEquals(2, map.size());
    }

    @Test
    void createTripMapWithTripId() {
        Map<BigInteger, Trip> map = fileUtil.createTripMapWithTripId("src/test/resources/GTFS_SL_TEST/trips.txt");
        assertFalse(map.isEmpty());
        assertEquals(31, map.size());
    }

    @Test
    void createRouteMapWithRouteId() {
        Map<String, Route> map = fileUtil.createRouteMapWithRouteId("src/test/resources/GTFS_SL_TEST/routes.txt");
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
    }

    @Test
    void createCalendarDateMapWithServiceId() {
        Map<BigInteger, List<CalendarDate>> map = fileUtil.createCalendarDateMapWithServiceId("src/test/resources/GTFS_SL_TEST/calendar_dates.txt");
        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void createStopIdMapWithStopName() {
        Map<String, List<BigInteger>> map = fileUtil.createStopIdMapWithStopName("src/test/resources/GTFS_SL_TEST/stops.txt");
        assertFalse(map.isEmpty());
        assertEquals(6, map.size());
    }

    @Test
    void createTripIdListMapWithServiceId() {
        Map<BigInteger, List<BigInteger>> map = fileUtil.createTripIdListMapWithServiceId("src/test/resources/GTFS_SL_TEST/trips.txt");

        assertFalse(map.isEmpty());
        assertEquals(10, map.size());
    }

    @Test
    void parseCsvToStop() {
        List<Stop> stopList = fileUtil.parseCsvToStop("src/test/resources/GTFS_SL_TEST/stops.txt");

        assertEquals(5, stopList.size());
        assertEquals("Stavsnäs", stopList.get(0).getStop_name());
    }

    @Test
    void parseCsvToStopTime() {
        List<StopTime> stopTimeList = fileUtil.parseCsvToStopTime("src/test/resources/GTFS_SL_TEST/stop-times.txt");
        assertEquals(32, stopTimeList.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", stopTimeList.get(0).getStop_headsign());
    }

    @Test
    void parseCsvToTrip() {
        List<Trip> tripList = fileUtil.parseCsvToTrip("src/test/resources/GTFS_SL_TEST/trips.txt");
        assertEquals(31, tripList.size());
        assertEquals("19", tripList.get(0).getService_id());
    }

    @Test
    void parseCsvToRoute() {
        List<Route> routeList = fileUtil.parseCsvToRoute("src/test/resources/GTFS_SL_TEST/routes.txt");
        assertEquals(1, routeList.size());
        assertEquals("Waxholmsbolaget", routeList.get(0).getRoute_desc());
    }

    @Test
    void parseCsvToCalendar() {
        List<CalendarGtfs> calendarGtfsList = fileUtil.parseCsvToCalendar("src/test/resources/GTFS_SL_TEST/calendar.txt");
        assertEquals(10, calendarGtfsList.size());
        assertEquals("228", calendarGtfsList.get(1).getService_id());
    }

    @Test
    void parseCsvToCalendarDate() {
        List<CalendarDate> calendarDateList = fileUtil.parseCsvToCalendarDate("src/test/resources/GTFS_SL_TEST/calendar_dates.txt");
        assertEquals(221, calendarDateList.size());
        assertEquals("20250202", calendarDateList.get(0).getDate());
    }

//    @Test
//    void createStopMapWithStopId() {
//    }
//
//    @Test
//    void createStopTimeMapWithTripId() {
//    }
//
//    @Test
//    void createCalenderMapWithServiceId() {
//    }
//
//    @Test
//    void unzip() {
//    }
//
//    @Test
//    void readZipFile() {
//    }
}