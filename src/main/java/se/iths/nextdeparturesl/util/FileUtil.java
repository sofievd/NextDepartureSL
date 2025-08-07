package se.iths.nextdeparturesl.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;


public class FileUtil {

    public void deleteFile(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!Files.isSymbolicLink(file.toPath())) {
                    //System.out.println(file);
                    deleteFile(file);
                }
            }
        }
        folder.delete();
//        return folder.delete();
        //  FileUtils.deleteQuietly(folder);

    }

    public void writeToFile(FileOutputStream outputStream, InputStream inputStream) {
        try (outputStream; inputStream) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            throw new RuntimeException("error writing zip-file, error message: " + e.getMessage(), e);
        }
    }
}
