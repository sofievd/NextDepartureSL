package se.iths.nextdeparturesl.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.iths.nextdeparturesl.model.*;
import se.iths.nextdeparturesl.model.Calendar;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * utils to handle files like parse them into objects and maps, or unzipping them.
 *
 * @author Sofie Van Dingenen
 */
public class GtfsFileHandler {

    private static final Logger logger = LogManager.getLogger();

    public GtfsFileHandler() {
    }

    public List<String> getStopNameList(String filePath) {
        logger.info("creating list of stop names");
        Set<String> stopNameSet = new HashSet<>();
        List<Stop> stopList = parseCsvToStop(filePath);
        for (Stop stop : stopList) {
            stopNameSet.add(stop.getStopName());
        }
        return stopNameSet.stream().toList();
    }

    public List<String> getServiceIDListFromTripList(List<Trip> tripList) {
        logger.info("creating list of service Ids from a list of trips");
        List<String> serviceIdList = new ArrayList<>();
        for (Trip trip : tripList) {
            String serviceId = trip.getServiceId();
            if (!serviceIdList.contains(serviceId)) {
                serviceIdList.add(serviceId);
            }
        }
        return serviceIdList;
    }

    public List<String> getStopIdListWithStopName(String searchString, List<Stop> stopList) {
        ArrayList<String> resultList = new ArrayList<>();
        for (Stop stop : stopList) {
            if (stop.getStopName().contains(searchString) && stop.getLocationType().equals("0")) {
                resultList.add(stop.getStopId());
            }
        }
        return resultList;
    }

    public Set<String> getTripListWithServiceId(String serviceId, List<Trip> tripList) {
        Set<String> resultList = new HashSet<>();
        for (Trip trip : tripList) {
            if (trip.getServiceId().equals(serviceId)) {
                resultList.add(trip.getTripId());
            }
        }
        return resultList;
    }

    public List<Stop> parseCsvToStop(String filePath) {
        logger.info("Starting parsing CSV Stop file");
        try {
            return new CsvToBeanBuilder<Stop>(new FileReader(filePath))
                    .withType(Stop.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", filePath);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<StopTime> parseCsvToStopTime(String path) {
        logger.info("Starting parsing CSV StopTime file");
        try {
            CsvToBeanBuilder<StopTime> stopTimeCsvToBeanBuilder = new CsvToBeanBuilder<>(new FileReader(path));
            return stopTimeCsvToBeanBuilder.withType(StopTime.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", path);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Trip> parseCsvToTrip(String path) {
        logger.info("Starting parsing CSV Trip file");
        try {
            return new CsvToBeanBuilder<Trip>(new FileReader(path))
                    .withType(Trip.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", path);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Route> parseCsvToRoute(String path) {
        logger.info("Starting parsing CSV Route file");
        try {
            return new CsvToBeanBuilder<Route>(new FileReader(path))
                    .withType(Route.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", path);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Calendar> parseCsvToCalendar(String path) {
        logger.info("Starting parsing CSV Calendar file");
        try {
            return new CsvToBeanBuilder<Calendar>(new FileReader(path))
                    .withType(Calendar.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", path);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<CalendarDate> parseCsvToCalendarDate(String path) {
        logger.info("Starting parsing CSV CalendarDate file");
        try {
            return new CsvToBeanBuilder<CalendarDate>(new FileReader(path))
                    .withType(CalendarDate.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file {}", path);
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
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
