package reports;

import java.io.IOException;
import java.util.List;

public interface IFilesProcessor {

    List<String> readFile(String fileName);

    void generateReport(List<String> lines, String fileName) throws IOException;
}
