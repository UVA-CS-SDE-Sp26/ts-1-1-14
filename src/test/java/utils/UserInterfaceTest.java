package utils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import utils.UserInterface;
import utils.InterfaceBridge;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class UserInterfaceTest {

    @Test
    public void testListFiles() {
        //Creates a mock bridge to simulate the interface without dependent logic
        InterfaceBridge mockBridge = Mockito.mock(InterfaceBridge.class);

        //Creates the user interface instance using the mock bridge
        UserInterface ui = new UserInterface(mockBridge);

        //Executes the run method with no arguments
        String[] args = {};
        ui.run(args);

        //Verifies that the interface correctly calls the list method exactly once
        verify(mockBridge, times(1)).getFilesForDisplay();
    }

    @Test
    public void testReadDefault() {
        //Creates a mock bridge to simulate the interface without dependent logic
        InterfaceBridge mockBridge = Mockito.mock(InterfaceBridge.class);

        //Creates the user interface instance using the mock bridge
        UserInterface ui = new UserInterface(mockBridge);

        //Executes the run method with a single file argument
        String[] args = {"01"};
        ui.run(args);

        //Verifies that the interface requests file contents with the default cipher (null)
        verify(mockBridge, times(1)).requestFileContents("01", null);
    }

    @Test
    public void testReadWithKey() {
        //Creates a mock bridge to simulate the interface without dependent logic
        InterfaceBridge mockBridge = Mockito.mock(InterfaceBridge.class);

        //Creates the user interface instance using the mock bridge
        UserInterface ui = new UserInterface(mockBridge);

        //Executes the run method with a file argument and a custom key
        String[] args = {"01", "superSecret"};
        ui.run(args);

        //Verifies that the interface requests file contents using the provided custom key
        verify(mockBridge, times(1)).requestFileContents("01", "superSecret");
    }
}
