package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GtfsFileHandlerTest {
    private GtfsFileHandler gtfsFileHandler;

    @BeforeEach
    void setUp() {
        gtfsFileHandler = new GtfsFileHandler();
    }

    @Test
    void getStopNameList() {
        List<String> stopnameList = gtfsFileHandler.getStopNameList("src/test/resources/GTFS_SL_TEST/stops.txt");
        assertEquals(5, stopnameList.size());
        assertTrue(stopnameList.contains("Idöborg"));
    }

    @Test
    void parseCsvToStop() {
        List<Stop> stopList = gtfsFileHandler.parseCsvToStop("src/test/resources/GTFS_SL_TEST/stops.txt");

        assertEquals(6, stopList.size());
        assertEquals("Stavsnäs", stopList.get(0).getStopName());
    }

    @Test
    void parseCsvToStopTime() {
        List<StopTime> stopTimeList = gtfsFileHandler.parseCsvToStopTime("src/test/resources/GTFS_SL_TEST/stop_times.txt");
        assertEquals(32, stopTimeList.size());
        assertEquals("Hagede via Styrsvik Långvik Sandhamn", stopTimeList.get(0).getStopHeadsign());
    }

    @Test
    void parseCsvToTrip() {
        List<Trip> tripList = gtfsFileHandler.parseCsvToTrip("src/test/resources/GTFS_SL_TEST/trips.txt");
        assertEquals(31, tripList.size());
        assertEquals("19", tripList.get(0).getServiceId());
    }

    @Test
    void parseCsvToRoute() {
        List<Route> routeList = gtfsFileHandler.parseCsvToRoute("src/test/resources/GTFS_SL_TEST/routes.txt");
        assertEquals(2, routeList.size());
        assertEquals("Waxholmsbolaget", routeList.get(0).getRouteDesc());
    }

    @Test
    void parseCsvToCalendar() {
        List<Calendar> calendarList = gtfsFileHandler.parseCsvToCalendar("src/test/resources/GTFS_SL_TEST/calendar.txt");
        assertEquals(10, calendarList.size());
        assertEquals("228", calendarList.get(1).getServiceId());
    }

    @Test
    void parseCsvToCalendarDate() {
        List<CalendarDate> calendarDateList = gtfsFileHandler.parseCsvToCalendarDate("src/test/resources/GTFS_SL_TEST/calendar_dates.txt");
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