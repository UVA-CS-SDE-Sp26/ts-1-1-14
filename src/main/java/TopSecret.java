/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Listing all files...");
            //Call Lamin's list method later
        }

        else if (args.length == 1) {
            String fileName = args[0];
            System.out.println("Reading file name: " + fileName);
            //Call Lamin's read method here later
        }

        else if (args.length == 2) {
            String fileName = args[0];
            String key = args[1];
            System.out.println("Reading file name: " + fileName + " using key: " + key);
            //Call Lamin's key method here later
        }

        else {
            System.out.println("Error: invalid command");
        }
    }
}
