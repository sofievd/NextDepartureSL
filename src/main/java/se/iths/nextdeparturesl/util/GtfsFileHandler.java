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
import java.util.List;


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
        List<Stop> stops = new ArrayList<>();
        logger.info("Starting to read stops.txt file and creating stop list");
        for (org.onebusaway.gtfs.model.Stop stopOneBusAway : store.getAllStops()) {
            Stop stop = createInMemoryStop(stopOneBusAway);
            stops.add(stop);
        }
        logger.info("Finished reading stops.txt file and creating stop list");
        return stops;
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
        logger.info("Starting to read stopTimes.txt file and creating stopTime list");
        List<StopTime> stopTimes = new ArrayList<>();
        for (org.onebusaway.gtfs.model.StopTime stopTimeOneBusAway : store.getAllStopTimes()) {
            StopTime stopTime = createInMemoryStopTime(stopTimeOneBusAway);
            stopTimes.add(stopTime);
        }
        logger.info("Finished reading stopTimes.txt file and creating stopTime list");
        return stopTimes;
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
        logger.info("Starting to read trips.txt file and creating trip list");
        List<Trip> trips = new ArrayList<>();
        for (org.onebusaway.gtfs.model.Trip tripOneBusAway : store.getAllTrips()) {
            Trip trip = createInMemoryTrip(tripOneBusAway);
            trips.add(trip);
        }
        logger.info("Finished reading trips.txt file and creating trip list");
        return trips;
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
        logger.info("Starting to read routes.txt file and creating route list");
        List<Route> routes = new ArrayList<>();
        for (org.onebusaway.gtfs.model.Route routeOneBusAway : store.getAllRoutes()) {
            Route route = createInMemoryRoute(routeOneBusAway);
            routes.add(route);
        }
        logger.info("Finished reading routes.txt file and creating route list");
        return routes;
    }

    private Route createInMemoryRoute(org.onebusaway.gtfs.model.Route routeOneBusAway) {
        return new Route(routeOneBusAway.getId().getId(),
                routeOneBusAway.getAgency().getId(),
                routeOneBusAway.getShortName(),
                routeOneBusAway.getLongName(),
                routeOneBusAway.getType(),
                routeOneBusAway.getDesc());
    }

    public List<Calendar> getAllCalendars(GtfsDaoImpl store) {
        logger.info("Starting to read calendars.txt file and creating calendar list");
        List<Calendar> calendars = new ArrayList<>();
        for (ServiceCalendar serviceCalendar : store.getAllCalendars()) {
            Calendar calendar = createInMemoryCalendar(serviceCalendar);
            calendars.add(calendar);
        }
        logger.info("Finished reading calendars.txt file and creating calendar list");
        return calendars;
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
        logger.info("Starting to read calendar_dates.txt file and creating calendarDate list");
        List<CalendarDate> calendarDates = new ArrayList<>();
        for (ServiceCalendarDate serviceCalendarDate : store.getAllCalendarDates()) {
            CalendarDate calendarDate = createInMemoryCalendarDate(serviceCalendarDate);
            calendarDates.add(calendarDate);
        }
        logger.info("Finished reading calendar_dates.txt file and creating calendarDate list");
        return calendarDates;
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

}
