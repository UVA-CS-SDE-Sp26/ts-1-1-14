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

        // Arrange
        Files.createFile(tempDir.resolve("filea.txt"));
        Files.createFile(tempDir.resolve("fileb.txt"));

        FileHandler handler = new FileHandler(tempDir);

        // Act
        List<String> files = handler.listFiles();

        // Assert
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
    void readFile_returnsCorrectContent(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello World");

        FileHandler handler = new FileHandler(tempDir);

        // Act
        String content = handler.readFile("test.txt");

        // Assert
        assertEquals("Hello World", content);
    }




}
