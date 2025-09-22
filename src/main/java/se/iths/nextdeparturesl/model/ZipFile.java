package se.iths.nextdeparturesl.model;

public class ZipFile {
    private String fileName;
    private String filePath;

    public ZipFile(String fileName) {
        this.fileName = fileName;
        this.filePath = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
