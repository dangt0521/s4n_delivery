package reports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesProcessor implements IFilesProcessor {

    private final Logger logger = LogManager.getLogger(FilesProcessor.class);

    @Override
    public List<String> readFile(String fileName) {

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.map(String::toUpperCase).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error while reading file: {}", e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void generateReport(List<String> lines, String fileName) throws IOException {
        Files.write(Paths.get(fileName),
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
