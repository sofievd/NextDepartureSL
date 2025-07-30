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
import java.util.zip.ZipEntry;

/**
 * utils to handle files like parse them into objects and maps, or unzipping them.
 *
 * @author Sofie Van Dingenen
 */
public class GtfsFileHandler {

    private static final Logger logger = LogManager.getLogger();
    private List<Stop> stopList = new ArrayList<>();
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
           // System.out.println(stopOneBusAway.getName());
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
                String.valueOf(stopTimeOneBusAway.getArrivalTime()),
                String.valueOf(stopTimeOneBusAway.getDepartureTime()),
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


    //TODO: implement One bus away
    //TODO: make sure that all maps are created as they should.


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


//    public List<Stop> parseCsvToStop(String filePath) {
//        logger.info("Starting parsing CSV Stop file");
//
//        try {
//            return new CsvToBeanBuilder<Stop>(new FileReader(filePath))
//                    .withType(Stop.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", filePath);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<StopTime> parseCsvToStopTime(String path) {
//        logger.info("Starting parsing CSV StopTime file");
//        try {
//            CsvToBeanBuilder<StopTime> stopTimeCsvToBeanBuilder = new CsvToBeanBuilder<>(new FileReader(path));
//            return stopTimeCsvToBeanBuilder.withType(StopTime.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", path);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Trip> parseCsvToTrip(String path) {
//        logger.info("Starting parsing CSV Trip file");
//        try {
//            return new CsvToBeanBuilder<Trip>(new FileReader(path))
//                    .withType(Trip.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", path);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Route> parseCsvToRoute(String path) {
//        logger.info("Starting parsing CSV Route file");
//        try {
//            return new CsvToBeanBuilder<Route>(new FileReader(path))
//                    .withType(Route.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", path);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Calendar> parseCsvToCalendar(String path) {
//        logger.info("Starting parsing CSV Calendar file");
//        try {
//            return new CsvToBeanBuilder<Calendar>(new FileReader(path))
//                    .withType(Calendar.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", path);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<CalendarDate> parseCsvToCalendarDate(String path) {
//        logger.info("Starting parsing CSV CalendarDate file");
//        try {
//            return new CsvToBeanBuilder<CalendarDate>(new FileReader(path))
//                    .withType(CalendarDate.class).build().parse();
//        } catch (FileNotFoundException e) {
//            logger.error("Could not find file {}", path);
//            logger.error(e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void unzip(String zipPath) throws IOException {
//        String pathname = "src/main/resources/static/GTFS_SL";
//        final File destinationFile = new File(pathname);
//        final byte[] buffer = new byte[1024];
//        final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
//        ZipEntry zipEntry = zipInputStream.getNextEntry();
//        while (zipEntry != null) {
//            File newFile = newFile(destinationFile, zipEntry);
//            if (newFile.isDirectory()) {
//                if (!newFile.isDirectory() && !newFile.mkdir()) {
//                    throw new IOException("failed to create directory" + newFile);
//                }
//            } else {
//                File parent = newFile.getParentFile();
//                if (!parent.isDirectory() && !parent.mkdir()) {
//                    throw new IOException("Failed to create directory" + parent);
//
//                }
//                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
//                int length;
//                while ((length = zipInputStream.read(buffer)) > 0) {
//                    fileOutputStream.write(buffer, 0, length);
//                }
//                fileOutputStream.close();
//            }
//            zipEntry = zipInputStream.getNextEntry();
//
//        }
//        zipInputStream.closeEntry();
//        zipInputStream.close();
//    }
//
//    public void readZipFile(String zipFilePath) throws IOException {
//        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
//            Enumeration<? extends ZipEntry> entries = zipFile.entries();
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = entries.nextElement();
//                if (!entry.isDirectory()) {
//
//                    try (InputStream inputStream = zipFile.getInputStream(entry);
//                         Scanner scanner = new Scanner(inputStream)) {
//                        while (scanner.hasNextLine()) {
//                            String line = scanner.nextLine();
//                            System.out.println(line);
//                        }
//                    }
//                }
//            }
//        }
//    }

    private File newFile(File destinationDirectory, ZipEntry zipEntry) throws IOException {
        File destinationFile = new File(destinationDirectory, zipEntry.getName());

        String destinationDirectoryPath = destinationDirectory.getCanonicalPath();
        String destinationFilePath = destinationFile.getCanonicalPath();

        if (!destinationFilePath.startsWith(destinationDirectoryPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destinationFile;
    }
}
