package se.iths.nextdeparturesl.util;

import com.opencsv.bean.CsvToBeanBuilder;


import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import se.iths.nextdeparturesl.model.*;
/**
 * utils to handle files like parse them into objects and maps, or unzipping them.
 *
 * @author Sofie Van Dingenen
 */
//TODO: adding error handling for when file reading fails
//TODO: adding tests,
//      1. creating test files
// TODO: refactoring,
//      1. moving creating maps to Mapservice,
//      2. having methods where you put in your files as parameter for testing,
//      3.returing list of objects for calling in map service
public class FileUtil {
    private final String STOP_FILE_PATH = "src/main/resources/static/GTFS_SL/stops.txt";
    private final String STOP_TIMES_FILE_PATH = "src/main/resources/static/GTFS_SL/stop_times.txt";
    private final String TRIP_FILE_PATH = "src/main/resources/static/GTFS_SL/trips.txt";
    private final String ROUTE_FILE_PATH = "src/main/resources/static/GTFS_SL/routes.txt";
    private final String CALENDAR_FILE_PATH = "src/main/resources/static/GTFS_SL/calendar.txt";
    private final String CALENDAR_DATE_FILE_PATH = "src/main/resources/static/GTFS_SL/calendar_dates.txt";

    public FileUtil() {
    }

    public List<String> getStationList() {
        return getStopNameList(STOP_FILE_PATH).stream().toList();
    }

    public Set<String> getStopNameList(String filePath) {
        Set<String> stopNameList = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                stopNameList.add(values[1].toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + filePath);
        }
        return stopNameList;
    }

    public Map<BigInteger, List<StopTime>> createStopTimeMapWithStopId() {
        Map<BigInteger, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = parseCsvToStopTime();
        for (StopTime stopTime : stopTimeList) {
            BigInteger stopId = new BigInteger(stopTime.getStop_id());
            if (map.containsKey(stopId)) {
                map.get(stopId).add(stopTime);
            } else {
                List<StopTime> stopTimes = new ArrayList<>();
                stopTimes.add(stopTime);
                map.put(stopId, stopTimes);
            }
        }
        return map;
    }

    public Map<BigInteger, Trip> createTripMapWithTripId() {
        Map<BigInteger, Trip> map = new HashMap<>();
        List<Trip> tripList = parseCsvToTrip();
        for (Trip trip : tripList) {
            BigInteger tripId = new BigInteger(trip.getTrip_id());
            map.put(tripId, trip);
        }
        return map;
    }

    public Map<String, Route> createRouteMapWithRouteId() {
        Map<String, Route> map = new HashMap<>();
        try {
            List<Route> routeList = parseCsvToRoute();
            for (Route route : routeList) {
                String routeId = route.getRoute_id();
                map.put(routeId, route);
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + ROUTE_FILE_PATH);
        }
        return map;
    }

    public Map<BigInteger, List<CalendarDate>> createCalendarDateMapWithServiceId() {
        Map<BigInteger, List<CalendarDate>> map = new HashMap<>();
        try {
            List<CalendarDate> calendarDateList = parseCsvToCalendarDate();
            for (CalendarDate calendar : calendarDateList) {
                BigInteger calendarDateId = new BigInteger(calendar.getService_id());
                if (map.containsKey(calendarDateId)) {
                    map.get(calendarDateId).add(calendar);
                } else {
                    List<CalendarDate> calendarDates = new ArrayList<>();
                    calendarDates.add(calendar);
                    map.put(calendarDateId, calendarDates);
                }
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + CALENDAR_DATE_FILE_PATH);
        }
        return map;
    }

    public Map<String, List<BigInteger>> createStopIdMapWithStopName() {
        Map<String, List<BigInteger>> map = new HashMap<>();
        List<String> nameList = getStopNameList(STOP_FILE_PATH).stream().toList();
        for (String name : nameList) {
            List<BigInteger> stopIdList = getStopIdListWithStopName(name);
            map.put(name, stopIdList);
        }
        return map;
    }


    public Map<BigInteger, List<BigInteger>> createTripIdListMapWithServiceId() {
        Map<BigInteger, List<BigInteger>> map = new HashMap<>();
        List<Trip> tripList = parseCsvToTrip();
        List<BigInteger> serviceIdList = getServiceIDListFromTripList(tripList);
        for (BigInteger serviceId : serviceIdList) {
            if (!map.containsKey(serviceId)) {
                List<BigInteger> tripIdList = getTripListWithServiceId(serviceId).stream().toList();
                map.put(serviceId, tripIdList);
            }
        }
        return map;
    }


    private List<BigInteger> getServiceIDListFromTripList(List<Trip> tripList) {
        List<BigInteger> serviceIdList = new ArrayList<>();
        for (Trip trip : tripList) {
            BigInteger serviceId = new BigInteger(trip.getService_id());
            if (!serviceIdList.contains(serviceId)) {
                serviceIdList.add(serviceId);
            }
        }
        return serviceIdList;
    }

    private List<BigInteger> getStopIdListWithStopName(String searchString) {
        ArrayList<BigInteger> resultList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(STOP_FILE_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].toLowerCase().contains(searchString.toLowerCase()) && values[4].equals("0")) {
                    resultList.add(new BigInteger(values[0]));
                }
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + "src/main/resources/GTFS_SL/stops.txt");
        }

        return resultList;
    }

    private Set<BigInteger> getTripListWithServiceId(BigInteger serviceId) {
        Set<BigInteger> resultList = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(TRIP_FILE_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].contains(serviceId.toString())) {
                    resultList.add(new BigInteger(values[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + "src/main/resources/GTFS_SL/stops.txt");
        }
        return resultList;
    }

    public List<Stop> parseCsvToStop(String filePath) {
        try {
            return new CsvToBeanBuilder<Stop>(new FileReader(filePath))
                    .withType(Stop.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stop> getStopList(){
        return parseCsvToStop(STOP_FILE_PATH);
    }
    public List<StopTime> parseCsvToStopTime() {
        try {
            return new CsvToBeanBuilder<StopTime>(new FileReader(STOP_TIMES_FILE_PATH))
                    .withType(StopTime.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Trip> parseCsvToTrip() {
        try {
            return new CsvToBeanBuilder<Trip>(new FileReader(TRIP_FILE_PATH))
                    .withType(Trip.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Route> parseCsvToRoute() throws FileNotFoundException {
        return new CsvToBeanBuilder<Route>(new FileReader(ROUTE_FILE_PATH))
                .withType(Route.class).build().parse();
    }

    public List<CalendarGtfs> parseCsvToCalendar() throws FileNotFoundException {
        return new CsvToBeanBuilder<CalendarGtfs>(new FileReader(CALENDAR_FILE_PATH))
                .withType(CalendarGtfs.class).build().parse();
    }

    public List<CalendarDate> parseCsvToCalendarDate() throws FileNotFoundException {
        return new CsvToBeanBuilder<CalendarDate>(new FileReader(CALENDAR_DATE_FILE_PATH))
                .withType(CalendarDate.class).build().parse();
    }

    public Map<BigInteger, Stop> createStopMapWithStopId() {
        Map<BigInteger, Stop> map = new HashMap<>();

        List<Stop> stopList = getStopList();
        for (Stop stop : stopList) {
            BigInteger stopId = new BigInteger(stop.getStop_id());
            map.put(stopId, stop);
        }
//        }{
//
//            throw new RuntimeException("File could not be found" + STOP_FILE_PATH);
//            //System.out.println("file could not be found:" + STOP_FILE_PATH);
//        }
        return map;
    }

    public Map<BigInteger, List<StopTime>> createStopTimeMapWithTripId() {
        Map<BigInteger, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = parseCsvToStopTime();
        for (StopTime stopTime : stopTimeList) {
            BigInteger tripId = new BigInteger(stopTime.getTrip_id());
            if (map.containsKey(tripId)) {
                map.get(tripId).add(stopTime);
            } else {
                List<StopTime> stopTimes = new ArrayList<>();
                stopTimes.add(stopTime);
                map.put(tripId, stopTimes);
            }
        }
        return map;
    }

    public Map<BigInteger, CalendarGtfs> createCalenderMapWithServiceId() {
        Map<BigInteger, CalendarGtfs> map = new HashMap<>();
        try {
            List<CalendarGtfs> calendarList = parseCsvToCalendar();
            for (CalendarGtfs calendar : calendarList) {
                BigInteger calendarId = new BigInteger(calendar.getService_id());
                map.put(calendarId, calendar);
            }
        } catch (IOException e) {
            System.out.println("file could not be found:" + CALENDAR_FILE_PATH);
        }
        return map;
    }

    public void unzip(String zipPath) throws IOException {
        String pathname = "src/main/resources/static/GTFS_SL";
        final File destinationFile = new File(pathname);
        final byte[] buffer = new byte[1024];
        final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destinationFile, zipEntry);
            if (newFile.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("failed to create directory" + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory" + parent);

                }
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.close();
            }
            zipEntry = zipInputStream.getNextEntry();

        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }

    public void readZipFile(String zipFilePath) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {

                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         Scanner scanner = new Scanner(inputStream)) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(line);
                        }
                    }
                }
            }
        }
    }

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
