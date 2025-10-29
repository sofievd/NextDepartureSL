package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.serialization.GtfsReader;
import se.iths.nextdeparturesl.dto.Station;
import se.iths.nextdeparturesl.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GtfsFileHandlerTest {
    private GtfsFileHandler gtfsFileHandler;
    private GtfsDaoImpl store;

    @BeforeEach
    void setUp() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        gtfsFileHandler = new GtfsFileHandler();

        GtfsReader reader = new GtfsReader();
        store = new GtfsDaoImpl();
        try {
            reader.setInputLocation(file);
            reader.setEntityStore(store);
            reader.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void getAllStops() {
        List<Stop> stopList = gtfsFileHandler.getAllStops(store);
        assertEquals(6, stopList.size());
        assertEquals("Idöborg", stopList.get(1).getStopName());
    }

    @Test
    void getAllStopTimes() {
        List<StopTime> stopTimeList = gtfsFileHandler.getAllStopTimes(store);
        assertEquals(32, stopTimeList.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", stopTimeList.get(0).getStopHeadsign());
    }

    @Test
    void getAllTrips() {
        List<Trip> tripList = gtfsFileHandler.getAllTrips(store);
        assertEquals(32, tripList.size());
        assertEquals("19", tripList.get(0).getServiceId());
    }

    @Test
    void getAllRoutes() {
        List<Route> routeList = gtfsFileHandler.getAllRoutes(store);
        assertEquals(3, routeList.size());
        assertEquals("Waxholmsbolaget", routeList.get(0).getDesc());
    }

    @Test
    void getAllCalendars() {
        List<Calendar> calendarList = gtfsFileHandler.getAllCalendars(store);
        assertEquals(11, calendarList.size());
        assertEquals("228", calendarList.get(1).getServiceId());

    }

    @Test
    void getAllCalendarDates() {
        List<CalendarDate> calendarDateList = gtfsFileHandler.getAllCalendarDates(store);
        assertEquals(222, calendarDateList.size());
        assertEquals("20250202", calendarDateList.get(0).getDate());
    }

    @Test
    void getStopList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<Stop> stopList = fileHandler.getStopList();
        assertEquals(6, stopList.size());
    }

    @Test
    void getStopTimeList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<StopTime> stopTimeList = fileHandler.getStopTimeList();
        assertEquals(32, stopTimeList.size());
        assertNotNull(stopTimeList);
    }

    @Test
    void getTripList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<Trip> tripList = fileHandler.getTripList();
        assertNotNull(tripList);
    }

    @Test
    void getRouteList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<Route> routeList = fileHandler.getRouteList();
        assertNotNull(routeList);
    }

    @Test
    void getCalendarList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<Calendar> calendarList = fileHandler.getCalendarList();
        assertNotNull(calendarList);
    }

    @Test
    void getCalendarDateList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<CalendarDate> calendarDateList = fileHandler.getCalendarDateList();
        assertNotNull(calendarDateList);
    }

   /* @Test
    void testGetStopNameList() {
        File file = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        GtfsFileHandler fileHandler = new GtfsFileHandler(file);
        List<Station> stopnameList = fileHandler.getStopNameList();
        assertEquals(6, stopnameList.size());
        assertTrue(stopnameList.contains("Idöborg"));
    }*/
}