package se.iths.nextdeparturesl.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.*;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * utils to handle files like parse them into objects and maps, or unzipping them.
 *
 * @author Sofie Van Dingenen
 */
public class FileUtil {

private static final Logger logger = LogManager.getLogger();
    public FileUtil() {
    }

    public List<String> getStopNameList(String filePath) {
        logger.info("creating list of stop names");
        Set<String> stopNameSet = new HashSet<>();
        List<Stop> stopList = parseCsvToStop(filePath);
        for (Stop stop : stopList) {
            stopNameSet.add(stop.getStop_name());
        }
        return stopNameSet.stream().toList();
    }


    public List<BigInteger> getServiceIDListFromTripList(List<Trip> tripList) {
        List<BigInteger> serviceIdList = new ArrayList<>();
        for (Trip trip : tripList) {
            BigInteger serviceId = new BigInteger(trip.getService_id());
            if (!serviceIdList.contains(serviceId)) {
                serviceIdList.add(serviceId);
            }
        }
        return serviceIdList;
    }

    public List<BigInteger> getStopIdListWithStopName(String searchString, String path) {
        ArrayList<BigInteger> resultList = new ArrayList<>();
        List<Stop> stopList = parseCsvToStop(path);
        for (Stop stop : stopList) {
            if (stop.getStop_name().contains(searchString) && stop.getLocation_type().equals("0") ) {
                resultList.add(new BigInteger(stop.getStop_id()));
            }
        }
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] values = line.split(",");
//                if (values[1].toLowerCase().contains(searchString.toLowerCase()) && values[4].equals("0")) {
//                    resultList.add(new BigInteger(values[0]));
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("file could not be found:" + "src/main/resources/GTFS_SL/stops.txt");
//        }

        return resultList;
    }

    public Set<BigInteger> getTripListWithServiceId(BigInteger serviceId, List<Trip> tripList) {
        Set<BigInteger> resultList = new HashSet<>();
        for(Trip trip : tripList) {
            if (new BigInteger(trip.getService_id()).equals(serviceId)) {
                resultList.add(new BigInteger(trip.getTrip_id()));
            }
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


    public List<StopTime> parseCsvToStopTime(String path) {
        try {
            return new CsvToBeanBuilder<StopTime>(new FileReader(path))
                    .withType(StopTime.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Trip> parseCsvToTrip(String path) {
        try {
            return new CsvToBeanBuilder<Trip>(new FileReader(path))
                    .withType(Trip.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Route> parseCsvToRoute(String path) {
        try {
            return new CsvToBeanBuilder<Route>(new FileReader(path))
                    .withType(Route.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<CalendarGtfs> parseCsvToCalendar(String path) {
        try {
            return new CsvToBeanBuilder<CalendarGtfs>(new FileReader(path))
                    .withType(CalendarGtfs.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CalendarDate> parseCsvToCalendarDate(String path) {
        try {
            return new CsvToBeanBuilder<CalendarDate>(new FileReader(path))
                    .withType(CalendarDate.class).build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public Map<BigInteger, Stop> createStopMapWithStopId(String path) {
        Map<BigInteger, Stop> map = new HashMap<>();
        List<Stop> stopList = parseCsvToStop(path);
        for (Stop stop : stopList) {
            BigInteger stopId = new BigInteger(stop.getStop_id());
            map.put(stopId, stop);
        }
        return map;
    }

    public Map<BigInteger, List<StopTime>> createStopTimeMapWithTripId(String path) {
        Map<BigInteger, List<StopTime>> map = new HashMap<>();
        List<StopTime> stopTimeList = parseCsvToStopTime(path); // getStopTimeList();
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

    public Map<BigInteger, CalendarGtfs> createCalenderMapWithServiceId(String path) {
        Map<BigInteger, CalendarGtfs> map = new HashMap<>();
        List<CalendarGtfs> calendarList = parseCsvToCalendar(path);
        for (CalendarGtfs calendar : calendarList) {
            BigInteger calendarId = new BigInteger(calendar.getService_id());
            map.put(calendarId, calendar);
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
