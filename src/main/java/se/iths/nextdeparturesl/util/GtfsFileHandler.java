package se.iths.nextdeparturesl.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.serialization.GtfsReader;
import se.iths.nextdeparturesl.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * utils to handle files like parse them into objects and maps, or unzipping them.
 *
 * @author Sofie Van Dingenen
 */
public class GtfsFileHandler {

    private static final Logger logger = LogManager.getLogger();
    private List<Stop> stopList;
    private List<StopTime> stopTimeList;
    private List<Trip> tripList;
    private List<Route> routeList;
    private List<Calendar> calendarList;
    private List<CalendarDate> calendarDateList;

    public GtfsFileHandler(File file) {
        parseFilesToObject(file);
    }

    public GtfsFileHandler() {
    }

    private void parseFilesToObject(File file) {
        GtfsReader reader = new GtfsReader();
        GtfsDaoImpl store = new GtfsDaoImpl();
        try {
            reader.setInputLocation(file);
            reader.setEntityStore(store);
            reader.run();
            stopList = getAllStops(store);
            stopTimeList = getAllStopTimes(store);
            tripList = getAllTrips(store);
            routeList = getAllRoutes(store);
            calendarList = getAllCalendars(store);
            calendarDateList = getAllCalendarDates(store);
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public GtfsDaoImpl setUp(File file) {
        GtfsReader reader = new GtfsReader();
        GtfsDaoImpl store = new GtfsDaoImpl();
        try {
            reader.setInputLocation(file);
            reader.setEntityStore(store);
            reader.run();
        } catch (IOException e) {
            logger.error(e);
        }
        return store;
    }

    public List<Stop> getAllStops(GtfsDaoImpl store) {
        List<Stop> stopList = new ArrayList<>();
        for (org.onebusaway.gtfs.model.Stop stopOneBusAway : store.getAllStops()) {
            Stop stop = createInMemoryStop(stopOneBusAway);
            stopList.add(stop);
        }
        return stopList;
    }

    private Stop createInMemoryStop(org.onebusaway.gtfs.model.Stop stopOneBusAway) {
        return new Stop(stopOneBusAway.getId().getId(),
                stopOneBusAway.getName(),
                stopOneBusAway.getLat(),
                stopOneBusAway.getLon(),
                stopOneBusAway.getLocationType(),
                stopOneBusAway.getParentStation(),
                stopOneBusAway.getPlatformCode()
        );
    }

    public List<StopTime> getAllStopTimes(GtfsDaoImpl store) {
        List<StopTime> stopTimeList = new ArrayList<>();
        for (org.onebusaway.gtfs.model.StopTime stopTimeOneBusAway : store.getAllStopTimes()) {
            StopTime stopTime = createInMemoryStopTime(stopTimeOneBusAway);
            stopTimeList.add(stopTime);
        }
        return stopTimeList;
    }

    private StopTime createInMemoryStopTime(org.onebusaway.gtfs.model.StopTime stopTimeOneBusAway) {
        return new StopTime(stopTimeOneBusAway.getTrip().getId().getId(),
                stopTimeOneBusAway.getArrivalTime(),
                stopTimeOneBusAway.getDepartureTime(),
                stopTimeOneBusAway.getStop().getId().getId(),
                stopTimeOneBusAway.getStopSequence(),
                stopTimeOneBusAway.getStopHeadsign(),
                stopTimeOneBusAway.getPickupType(),
                stopTimeOneBusAway.getDropOffType(),
                stopTimeOneBusAway.getShapeDistTraveled(),
                stopTimeOneBusAway.getTimepoint());
    }

    public List<Trip> getAllTrips(GtfsDaoImpl store) {
        List<Trip> tripList = new ArrayList<>();
        for (org.onebusaway.gtfs.model.Trip tripOneBusAway : store.getAllTrips()) {
            Trip trip = createInMemoryTrip(tripOneBusAway);
            tripList.add(trip);
        }
        return tripList;
    }

    private Trip createInMemoryTrip(org.onebusaway.gtfs.model.Trip tripOneBusAway) {
        return new Trip(tripOneBusAway.getId().getId(),
                tripOneBusAway.getRoute().getId().getId(),
                tripOneBusAway.getServiceId().getId(),
                tripOneBusAway.getTripHeadsign(),
                tripOneBusAway.getDirectionId(),
                tripOneBusAway.getShapeId().getId());
    }

    public List<Route> getAllRoutes(GtfsDaoImpl store) {
        List<Route> routeList = new ArrayList<>();
        for (org.onebusaway.gtfs.model.Route routeOneBusAway : store.getAllRoutes()) {
            Route route = createInMemoryRoute(routeOneBusAway);
            routeList.add(route);
        }
        return routeList;
    }

    private Route createInMemoryRoute(org.onebusaway.gtfs.model.Route routeOneBusAway) {
        return new Route(routeOneBusAway.getId().getId(),
                routeOneBusAway.getAgency().getId(),
                routeOneBusAway.getShortName(),
                routeOneBusAway.getLongName(),
                String.valueOf(routeOneBusAway.getType()),
                routeOneBusAway.getDesc());
    }

    public List<Calendar> getAllCalendars(GtfsDaoImpl store) {
        List<Calendar> calendarList = new ArrayList<>();
        for (ServiceCalendar serviceCalendar : store.getAllCalendars()) {
            Calendar calendar = createInMemoryCalendar(serviceCalendar);
            calendarList.add(calendar);
        }
        return calendarList;
    }

    private Calendar createInMemoryCalendar(ServiceCalendar serviceCalendar) {
        return new Calendar(serviceCalendar.getServiceId().getId(),
                serviceCalendar.getMonday(),
                serviceCalendar.getTuesday(),
                serviceCalendar.getWednesday(),
                serviceCalendar.getThursday(),
                serviceCalendar.getFriday(),
                serviceCalendar.getSaturday(),
                serviceCalendar.getSunday(),
                serviceCalendar.getStartDate().getAsString(),
                serviceCalendar.getEndDate().getAsString());
    }

    public List<CalendarDate> getAllCalendarDates(GtfsDaoImpl store) {
        List<CalendarDate> calendarDateList = new ArrayList<>();
        for (ServiceCalendarDate serviceCalendarDate : store.getAllCalendarDates()) {
            CalendarDate calendarDate = createInMemoryCalendarDate(serviceCalendarDate);
            calendarDateList.add(calendarDate);
        }
        return calendarDateList;
    }

    private CalendarDate createInMemoryCalendarDate(ServiceCalendarDate serviceCalendarDate) {
        return new CalendarDate(serviceCalendarDate.getServiceId().getId(),
                serviceCalendarDate.getDate().getAsString(),
                serviceCalendarDate.getExceptionType());
    }


    public List<Stop> getStopList() {
        return stopList;
    }

    public List<StopTime> getStopTimeList() {
        return stopTimeList;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public List<Calendar> getCalendarList() {
        return calendarList;
    }

    public List<CalendarDate> getCalendarDateList() {
        return calendarDateList;
    }

    public List<String> getStopNameList() {
        logger.info("creating list of stop names");
        Set<String> stopNameSet = new HashSet<>();
        List<Stop> stopList = this.stopList;
        for (Stop stop : stopList) {
            stopNameSet.add(stop.getStopName());
        }
        return stopNameSet.stream().toList();
    }
}
