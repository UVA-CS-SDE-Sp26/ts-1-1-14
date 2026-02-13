package utils.contracts;

import java.util.List;

public interface FileRepository {
   String readFile(String filename) throws Exception;
   List<String> listFiles() throws Exception;
}
