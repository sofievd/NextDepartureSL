package se.iths.nextdeparturesl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    FileUtil fileUtil = new FileUtil();
    File directory;
    @BeforeEach
    void setUp() {
        directory = new File("src/test/resources/test");
        if (!directory.exists()){
            directory.mkdirs();
        }
    }


    @Test
    void deleteFile() {
        assertTrue(directory.exists());
        fileUtil.deleteFile(directory);
      // System.out.println(fileUtil.deleteFile(theDir));
               //assertFalse(theDir.exists());
      // assertTrue(fileUtil.deleteFile(theDir));
        assertFalse(directory.exists());
    }

    @Test
    void writeToFile() {
        File theFile = new File(directory, "test.txt");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(theFile.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String text = "testing putting in the file";
        InputStream inputStream = new ByteArrayInputStream(text.getBytes());

        fileUtil.writeToFile(outputStream, inputStream);

        assertTrue(theFile.exists());
        assertTrue(theFile.canRead());
        assertTrue(theFile.canWrite());
    }
}