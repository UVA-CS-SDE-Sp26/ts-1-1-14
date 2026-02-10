//Temporary placeholder for Lamin's code in order to test user interface.

public class InterfaceBridge {
    //Returns fake data to test file display
    public String getFilesForDisplay() {
        return "01 file_a.txt\n02 file_b.txt\n03 file_c.txt";
    }

    //Returns fake data to test reading/deciphering with both default and custom keys
    public String requestFileContents(String selection, String cipher) {
        if (cipher == null) {
            return "This is the content of the file " + selection + " using the default cipher key.";
        }
        else {
            return "This is the content of the file " + selection + " using custom cipher key: " + cipher;
        }
    }
}
