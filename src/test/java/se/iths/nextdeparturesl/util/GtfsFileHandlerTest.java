package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import se.iths.nextdeparturesl.model.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GtfsFileHandlerTest {
    private GtfsFileHandler gtfsFileHandler;
    private GtfsDaoImpl store;

    @BeforeEach
    void setUp() {
        File file = new File("src/test/resources/test_sl.zip");
        gtfsFileHandler = new GtfsFileHandler();
        store = gtfsFileHandler.setUp(file);

    }

    @Test
    void getAllStops() {
        List<Stop> stopList = gtfsFileHandler.getAllStops(store);
        assertEquals(5, stopList.size());
        assertEquals("Idöborg", stopList.get(0).getStopName());
    }

    @Test
    void getAllStopTimes() {
        List<StopTime> stopTimeList = gtfsFileHandler.getAllStopTimes(store);
        assertEquals(31, stopTimeList.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", stopTimeList.get(0).getStopHeadsign());
    }

    @Test
    void getAllTrips() {
        List<Trip> tripList = gtfsFileHandler.getAllTrips(store);
        assertEquals(31, tripList.size());
        assertEquals("19", tripList.get(0).getServiceId());
    }

    @Test
    void getAllRoutes() {
        List<Route> routeList = gtfsFileHandler.getAllRoutes(store);
        assertEquals(2, routeList.size());
       assertEquals("Waxholmsbolaget", routeList.get(0).getDesc());
    }

    @Test
    void getAllCalendars() {
        List<Calendar> calendarList = gtfsFileHandler.getAllCalendars(store);
        assertEquals(10, calendarList.size());
        assertEquals("228", calendarList.get(1).getServiceId());

    }

    @Test
    void getAllCalendarDates() {
        List<CalendarDate> calendarDateList = gtfsFileHandler.getAllCalendarDates(store);
        assertEquals(221, calendarDateList.size());
        assertEquals("20250202", calendarDateList.get(0).getDate());
    }

    @Test
    void getStopList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<Stop> stopList = gtfsFileHandler.getStopList();
        assertEquals(5, stopList.size());
    }

    @Test
    void getStopTimeList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<StopTime> stopTimeList = gtfsFileHandler.getStopTimeList();
        assertEquals(31, stopTimeList.size());
        assertNotNull(stopTimeList);
    }

    @Test
    void getTripList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<Trip> tripList = gtfsFileHandler.getTripList();
        assertNotNull(tripList);
    }

    @Test
    void getRouteList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<Route> routeList = gtfsFileHandler.getRouteList();
        assertNotNull(routeList);
    }

    @Test
    void getCalendarList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<Calendar> calendarList = gtfsFileHandler.getCalendarList();
        assertNotNull(calendarList);
    }

    @Test
    void getCalendarDateList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<CalendarDate> calendarDateList = gtfsFileHandler.getCalendarDateList();
        assertNotNull(calendarDateList);
    }

    @Test
    void testGetStopNameList() {
        File file = new File("src/test/resources/test_sl.zip");
        GtfsFileHandler gtfsFileHandler = new GtfsFileHandler(file);
        List<String> stopnameList = gtfsFileHandler.getStopNameList();
        assertEquals(5, stopnameList.size());
        assertTrue(stopnameList.contains("Idöborg"));
    }
}