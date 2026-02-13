package utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import utils.contracts.FileRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class InterfaceBridgeTest {

    // simple key: normalKey="ab", cypherKey="ba" — so 'b' decrypts to 'a', 'a' to 'b'
    private static final String TEST_KEY = "ab\nba";

    // requestFileContents happy paths
    @Test
    void requestFileContents_returnsContent() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("secret.txt"));
        when(fileRepo.readFile("secret.txt")).thenReturn("ba");
        when(cipherRepo.readFile("key.txt")).thenReturn(TEST_KEY);

        String result = bridge.requestFileContents("01", "key.txt");
        assertNotNull(result);
        assertEquals("ab", result);
    }

    @Test
    void requestFileContents_callsDependencies() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("data.txt"));
        when(fileRepo.readFile("data.txt")).thenReturn("ba");
        when(cipherRepo.readFile("key.txt")).thenReturn(TEST_KEY);

        bridge.requestFileContents("01", "key.txt");

        verify(fileRepo).readFile("data.txt");
        verify(cipherRepo).readFile("key.txt");
    }

    // getFilesForDisplay happy paths

    @Test
    void getFilesForDisplay_returnsNumberedFileList() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("filea.txt", "fileb.txt", "filec.txt"));

        String result = bridge.getFilesForDisplay();
        String expected = "01 filea.txt\n02 fileb.txt\n03 filec.txt\n";
        assertEquals(expected, result);
    }

    @Test
    void getFilesForDisplay_callsFileRepo() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("file.txt"));

        bridge.getFilesForDisplay();
        verify(fileRepo).listFiles();
    }

    // edge cases

    @Test
    void requestFileContents_fileNotFound() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("file.txt"));

        String result = bridge.requestFileContents("99", "key.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("not found"));
    }

    @Test
    void requestFileContents_invalidCipher() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("data.txt"));
        when(fileRepo.readFile("data.txt")).thenReturn("ba");
        when(cipherRepo.readFile("bad.txt")).thenReturn("invalidkey");

        String result = bridge.requestFileContents("01", "bad.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("invalid"));
    }

    @Test
    void requestFileContents_emptyCipherUsesDefault() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(Arrays.asList("data.txt"));
        when(fileRepo.readFile("data.txt")).thenReturn("ba");
        when(cipherRepo.readFile("key.txt")).thenReturn(TEST_KEY);

        String result = bridge.requestFileContents("01", null);
        String result2 = bridge.requestFileContents("01", "");
        assertEquals("ab", result);
        assertEquals("ab", result2);
    }

    @Test
    void requestFileContents_invalidSelection() {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        String result = bridge.requestFileContents(null, "key.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("invalid"));
    }

    @Test
    void getFilesForDisplay_emptyList() throws Exception {
        FileRepository fileRepo = mock(FileRepository.class);
        FileRepository cipherRepo = mock(FileRepository.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipherRepo);

        when(fileRepo.listFiles()).thenReturn(new ArrayList<>());

        String result = bridge.getFilesForDisplay();
        assertTrue(result.isEmpty() || result.toLowerCase().contains("no files"));
    }
}
