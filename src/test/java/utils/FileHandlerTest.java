package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class FileHandlerTest {

    @Test
    void listFiles_returnsAllFilesInDirectory(@TempDir Path tempDir) throws IOException {

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
}
