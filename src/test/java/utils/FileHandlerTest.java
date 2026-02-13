package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class FileHandlerTest {

    @Test
    void listFiles_returnsAllFiles(@TempDir Path tempDir) throws IOException {
        Files.createFile(tempDir.resolve("filea.txt"));
        Files.createFile(tempDir.resolve("fileb.txt"));

        FileHandler handler = new FileHandler(tempDir);

        List<String> files = handler.listFiles();

        assertTrue(files.contains("filea.txt"));
        assertTrue(files.contains("fileb.txt"));
    }

    @Test
    void listFiles_returnsEmptyList(@TempDir Path tempDir) throws IOException {
        FileHandler handler = new FileHandler(tempDir);

        List<String> files = handler.listFiles();

        assertTrue(files.isEmpty());
    }

    @Test
    void listFiles_throwsException_whenDirectoryDoesNotExist() {
        Path fakePath = Paths.get("non_existent_directory");
        FileHandler handler = new FileHandler(fakePath);

        assertThrows(IOException.class, handler::listFiles);
    }

    @Test
    void readFile_throwsException_whenFileDoesNotExist(@TempDir Path tempDir) {
        FileHandler handler = new FileHandler(tempDir);

        assertThrows(IOException.class, () -> handler.readFile("missing.txt"));
    }

    @Test
    void readFile_returnsCorrectContent(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello World");

        FileHandler handler = new FileHandler(tempDir);

        String content = handler.readFile("test.txt");

        assertEquals("Hello World", content);
    }

    @Test
    void readFile_returnsEmptyString_whenFileEmpty(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.createFile(file);

        FileHandler handler = new FileHandler(tempDir);

        String content = handler.readFile("empty.txt");

        assertEquals("", content);
    }

    @Test
    void readFile_throwsException_whenFilenameIsNull(@TempDir Path tempDir) {
        FileHandler handler = new FileHandler(tempDir);

        assertThrows(NullPointerException.class, () -> handler.readFile(null));
    }

    @Test
    void readFile_throwsException_whenFilenameIsEmpty(@TempDir Path tempDir) {
        FileHandler handler = new FileHandler(tempDir);

        assertThrows(IOException.class, () -> handler.readFile(""));
    }
}
