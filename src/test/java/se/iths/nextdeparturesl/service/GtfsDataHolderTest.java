package se.iths.nextdeparturesl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.nextdeparturesl.model.CalendarDate;
import se.iths.nextdeparturesl.model.Route;
import se.iths.nextdeparturesl.model.StopTime;
import se.iths.nextdeparturesl.model.Trip;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GtfsDataHolderTest {
    private GtfsDataHolder gtfsDataHolder;

    @BeforeEach
    void setUp() {
        File gtfsRootFolderPath = new File(getClass().getClassLoader().getResource("2025-08-18-sl.zip").getFile());
        gtfsDataHolder = GtfsDataHolder.getInstance(gtfsRootFolderPath);
    }

    @Test
    void getStationList() {
       assertNull(gtfsDataHolder.getStationList());
    }

    @Test
    void getStopIdToStopTimes() {
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
    void getTripIdToTrips() {
        assertNull(gtfsDataHolder.getTripIdToTrips());
    }
}