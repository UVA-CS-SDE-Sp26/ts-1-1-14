package utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import utils.contracts.CipherService;
import utils.contracts.FileRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class InterfaceBridgeTest {

    // requestFileContents happy paths
    @Test
    void requestFileContents_returnsContent() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.readFileFromData("secret.txt")).thenReturn("encrypted");
        when(cipher.decryptText(eq("encrypted"), anyString())).thenReturn("decrypted");

        String result = bridge.requestFileContents("secret.txt", "key.txt");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void requestFileContents_callsDependencies() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.readFileFromData("data.txt")).thenReturn("text");
        when(cipher.decryptText(eq("text"), anyString())).thenReturn("plain");

        bridge.requestFileContents("data.txt", "key.txt");

        verify(fileRepo).readFileFromData("data.txt");
        verify(cipher).decryptText(eq("text"), anyString());
    }

    // getFilesForDisplay happy paths

    @Test
    void getFilesForDisplay_returnsNumberedFileList() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.listFilesInData()).thenReturn(Arrays.asList("filea.txt", "fileb.txt", "filec.txt"));

        String result = bridge.getFilesForDisplay();
        String expected = "01 filea.txt\n02 fileb.txt\n03 filec.txt\n";
        assertEquals(expected, result);
    }

    @Test
    void getFilesForDisplay_callsFileRepo() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.listFilesInData()).thenReturn(Arrays.asList("file.txt"));

        bridge.getFilesForDisplay();
        verify(fileRepo).listFilesInData();
    }

    // edge cases

    @Test
    void requestFileContents_fileNotFound() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.readFileFromData("missing.txt")).thenReturn(null);

        String result = bridge.requestFileContents("missing.txt", "key.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("not found"));
    }

    @Test
    void requestFileContents_invalidCipher() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.readFileFromData("data.txt")).thenReturn("text");
        when(cipher.decryptText(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("bad key"));

        String result = bridge.requestFileContents("data.txt", "bad.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("invalid"));
    }

    @Test
    void requestFileContents_invalidSelection() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        String result = bridge.requestFileContents(null, "key.txt");
        assertTrue(result.toLowerCase().contains("error") || result.toLowerCase().contains("invalid"));
    }

    @Test
    void getFilesForDisplay_emptyList() {
        FileRepository fileRepo = mock(FileRepository.class);
        CipherService cipher = mock(CipherService.class);
        InterfaceBridge bridge = new InterfaceBridge(fileRepo, cipher);

        when(fileRepo.listFilesInData()).thenReturn(new ArrayList<>());

        String result = bridge.getFilesForDisplay();
        assertNotNull(result);
    }
}
