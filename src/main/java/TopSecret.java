/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {
        //Creates the bridge to use Lamin's code
        InterfaceBridge bridge = new InterfaceBridge();

        //Displays all readable files
        if(args.length == 0) {
            String result = bridge.getFilesForDisplay();
            System.out.println(result);
        }
        //Deciphers and returns the contents of the selected file with the basic cipher.
        else if (args.length == 1) {
            String fileID = args[0];

            try {
                String content = bridge.requestFileContents(fileID, null);
                System.out.println(content);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        //Deciphers and returns the contents of the selected files with a custom cipher.
        else if (args.length == 2) {
            String fileID = args[0];
            String key = args[1];

            try {
                String content = bridge.requestFileContents(fileID, key);
                System.out.println(content);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        //Returns error message if there are too many arguments provided and provides how to actually use the program
        else {
            System.out.println("Error: invalid command. Correct Usage: java topsecret [file_id] [optional_key]");
        }
    }
}
