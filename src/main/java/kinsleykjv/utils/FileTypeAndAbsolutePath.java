package kinsleykjv.utils;

public class FileTypeAndAbsolutePath {
    private IOInfo.FileLinkType fileType;
    private String fileAbsolutePath;

    public FileTypeAndAbsolutePath(IOInfo.FileLinkType fileType, String fileAbsolutePath) {
        this.fileType = fileType;
        this.fileAbsolutePath = fileAbsolutePath;
    }

    /**
     * @return the fileAbsolutePath
     */
    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    /**
     *
     *
     * /**
     *
     * @return the fileType
     */
    public IOInfo.FileLinkType getFileType() {
        return fileType;
    }

}
