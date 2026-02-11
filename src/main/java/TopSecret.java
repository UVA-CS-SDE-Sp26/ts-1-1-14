/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {
        //Create the real bridge that connects to the file reading logic
        InterfaceBridge realBridge = new InterfaceBridge();

        //Create the UserInterface, injecting the bridge so it can be tested later
        UserInterface ui = new UserInterface(realBridge);

        //Pass the command line arguments to the UI to start the program
        ui.run(args);
    }
}
