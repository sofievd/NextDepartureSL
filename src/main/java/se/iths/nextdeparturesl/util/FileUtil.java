package se.iths.nextdeparturesl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;


public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public void deleteFile(File folder) {
        log.info("Deleting file: {} " ,folder.getAbsolutePath());
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!Files.isSymbolicLink(file.toPath())) {
                    deleteFile(file);
                }
            }
        }
        log.info("Deleted file: {} ", folder.getAbsolutePath());
        folder.delete();
    }

    public void writeToFile(FileOutputStream outputStream, InputStream inputStream) {
        try (outputStream; inputStream) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            throw new RuntimeException("error writing zip-file, error message: {}" + e.getMessage(), e);
        }
    }

}
