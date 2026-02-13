package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.contracts.FileRepository;
public class FileHandler implements FileRepository {

    private Path dataDirectory;

    public FileHandler(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public List<String> listFiles() throws IOException {
        try (Stream<Path> paths = Files.list(dataDirectory)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public String readFile(String filename) throws IOException {
        Path filePath = dataDirectory.resolve(filename);
        return Files.readString(filePath);
    }
}
