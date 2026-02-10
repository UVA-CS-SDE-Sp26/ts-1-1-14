package utils;

public class InterfaceBridge {

    private final FileHandler fileHandler;
    private final Cipher cipher;

    public InterfaceBridge(FileHandler fh, Cipher c) {
        this.fileHandler = fh;
        this.cipher = c;
    }

    public String requestFileConents(String selection, String cipher) {
        return "";
    }
    public String getFilesForDisplay() {
       return "";
    }
}
