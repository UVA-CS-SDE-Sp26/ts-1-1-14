package utils.contracts;

import java.util.List;

public interface FileRepository {
   String readFileFromData(String filename);
   List<String> listFilesInData();
}
