package delivery;

import delivery.process.DeliveryProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reports.FilesProcessor;
import reports.IFilesProcessor;
import utils.DeliveryConstants;
import utils.PropertiesFileAccessor;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeliveryOrchestratorTest {

    private DeliveryOrchestrator orchestrator;
    private IFilesProcessor filesProcessor;
    private Path workingDir;

    @Before
    public void init() {
        Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
        this.filesProcessor = new FilesProcessor();
        this.orchestrator =
                new DeliveryOrchestrator(new DeliveryProcessor(), new FilesProcessor(), Integer.parseInt(properties.getProperty(DeliveryConstants.DRONES_SIMULTANEOUSLY)));
        this.workingDir = Path.of("", "src/test/resources/output");
    }

    private String getFilePath(String fileName) {
        return workingDir.resolve(fileName).toString();
    }

    @Test
    public void successfulDeliveriesTest() {
        orchestrator.startDelivery();

        List<String> drone01 = filesProcessor.readFile(getFilePath("out01.txt"));
        List<String> drone02 = filesProcessor.readFile(getFilePath("out02.txt"));
        List<String> drone03 = filesProcessor.readFile(getFilePath("out03.txt"));
        List<String> drone05 = filesProcessor.readFile(getFilePath("out05.txt"));
        List<String> drone06 = filesProcessor.readFile(getFilePath("out06.txt"));
        List<String> drone20 = filesProcessor.readFile(getFilePath("out20.txt"));

        assertEquals(drone01.size(), 2);
        assertEquals(drone02.size(), 4);
        assertEquals(drone03.size(), 4);
        assertEquals(drone05.size(), 2);
        assertEquals(drone06.size(), 4);
        assertEquals(drone20.size(), 4);

        assertEquals(drone01.get(0), "== Reporte de entregas ==".toUpperCase());
        assertEquals(drone01.get(1), "(-2, 4) direcci\u00f3n Occidente".toUpperCase());

        assertEquals(drone02.get(1), "(-2, 4) direcci\u00f3n Occidente".toUpperCase());
        assertEquals(drone02.get(2), "(-1, 3) direcci\u00f3n Sur".toUpperCase());
        assertEquals(drone02.get(3), "(0, 0) direcci\u00f3n Oriente".toUpperCase());

        assertEquals(drone03.get(1), "(-2, 4) direcci\u00f3n Occidente".toUpperCase());
        assertEquals(drone03.get(2), "(0, 3) direcci\u00f3n Oriente".toUpperCase());
        assertEquals(drone03.get(3), "(3, 4) direcci\u00f3n Sur".toUpperCase());

        assertEquals(drone05.get(1), "(0, 7) direcci\u00f3n Norte".toUpperCase());

        assertEquals(drone06.get(1), "(-1, -1) direcci\u00f3n Occidente".toUpperCase());
        assertEquals(drone06.get(2), "(-4, -2) direcci\u00f3n Norte".toUpperCase());
        assertEquals(drone06.get(3), "(-5, 1) direcci\u00f3n Occidente".toUpperCase());

        assertEquals(drone20.get(1), "(-2, 4) direcci\u00f3n Occidente".toUpperCase());
        assertEquals(drone20.get(2), "(-1, 3) direcci\u00f3n Sur".toUpperCase());
        assertEquals(drone20.get(3), "(-1, 1) direcci\u00f3n Occidente".toUpperCase());
    }

    @Test
    public void emptyFileTest() {
        orchestrator.startDelivery();

        List<String> drone09 = filesProcessor.readFile(getFilePath("out09.txt"));

        assertTrue(drone09.isEmpty());
    }

    @Test
    public void noFileTest() {
        orchestrator.startDelivery();

        List<String> drone11 = filesProcessor.readFile(getFilePath("out11.txt"));
        List<String> drone18 = filesProcessor.readFile(getFilePath("out18.txt"));
        List<String> drone19 = filesProcessor.readFile(getFilePath("out19.txt"));

        assertTrue(drone11.isEmpty());
        assertTrue(drone18.isEmpty());
        assertTrue(drone19.isEmpty());
    }

    @Test
    public void maxCarryingCapacityExceededTest() {
        orchestrator.startDelivery();

        List<String> drone04 = filesProcessor.readFile(getFilePath("out04.txt"));
        List<String> drone07 = filesProcessor.readFile(getFilePath("out07.txt"));
        List<String> drone08 = filesProcessor.readFile(getFilePath("out08.txt"));
        List<String> drone10 = filesProcessor.readFile(getFilePath("out10.txt"));
        List<String> drone12 = filesProcessor.readFile(getFilePath("out12.txt"));
        List<String> drone13 = filesProcessor.readFile(getFilePath("out13.txt"));
        List<String> drone14 = filesProcessor.readFile(getFilePath("out14.txt"));
        List<String> drone15 = filesProcessor.readFile(getFilePath("out15.txt"));
        List<String> drone16 = filesProcessor.readFile(getFilePath("out16.txt"));
        List<String> drone17 = filesProcessor.readFile(getFilePath("out17.txt"));

        assertTrue(drone04.isEmpty());
        assertTrue(drone07.isEmpty());
        assertTrue(drone08.isEmpty());
        assertTrue(drone10.isEmpty());
        assertTrue(drone12.isEmpty());
        assertTrue(drone13.isEmpty());
        assertTrue(drone14.isEmpty());
        assertTrue(drone15.isEmpty());
        assertTrue(drone16.isEmpty());
        assertTrue(drone17.isEmpty());
    }

    @Test
    public void maxSimultaneousDronesNumberExceededTest() {
        orchestrator.startDelivery();

        List<String> drone21 = filesProcessor.readFile(getFilePath("out21.txt"));
        List<String> drone25 = filesProcessor.readFile(getFilePath("out25.txt"));
        List<String> drone28 = filesProcessor.readFile(getFilePath("out28.txt"));
        List<String> drone30 = filesProcessor.readFile(getFilePath("out30.txt"));

        assertTrue(drone21.isEmpty());
        assertTrue(drone25.isEmpty());
        assertTrue(drone28.isEmpty());
        assertTrue(drone30.isEmpty());
    }

    @After
    public void deleteOutputFiles() {
        IntStream.rangeClosed(1, 20)
                .mapToObj(i -> i < 10 ? "0".concat(String.valueOf(i)) : String.valueOf(i))
                .map(fileId -> new File(getFilePath("out".concat(fileId).concat(".txt"))))
                .forEach(File::deleteOnExit);
    }
}
