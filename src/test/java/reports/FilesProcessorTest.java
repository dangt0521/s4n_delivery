package reports;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class FilesProcessorTest {

    private IFilesProcessor filesProcessor;
    private Path workingDir;
    private String outputFile;

    @Before
    public void init() {
        this.filesProcessor = new FilesProcessor();
        this.workingDir = Path.of("", "src/test/resources");
        this.outputFile = workingDir.resolve("testOutPut.txt").toString();
    }

    @Test
    public void readFileTest() throws IOException {
        Path file = workingDir.resolve("testFile.txt");
        List<String> result = filesProcessor.readFile(file.toString());
        assertFalse(result.isEmpty());
        assertEquals(result.get(0), "HELLO WORLD");
        assertEquals(result.get(1), "THIS IS ME");
        assertNotEquals(result.get(2), "GOOD BYE?");
    }

    @Test
    public void fileNotFoundTest() {
        Path file = workingDir.resolve("testFile1.txt");
        List<String> result = filesProcessor.readFile(file.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    public void generateReportTest() throws IOException {
        List<String> result = filesProcessor.readFile(outputFile);

        assertTrue(result.isEmpty());

        filesProcessor.generateReport(Arrays.asList("This", "is", "a", "Test"), outputFile);
        result = filesProcessor.readFile(outputFile);

        assertFalse(result.isEmpty());
        assertEquals(result.get(0), "THIS");
    }

    @Test(expected = NullPointerException.class)
    public void nullListGeneratingReportTest() throws IOException {
        filesProcessor.generateReport(null, outputFile);
    }

    @After
    public void deleteFiles() {
        File destroyFile = new File(outputFile);
        destroyFile.deleteOnExit();
    }
}
