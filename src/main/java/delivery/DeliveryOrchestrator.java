package delivery;

import delivery.parameters.Drone;
import delivery.process.IDeliveryProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reports.IFilesProcessor;
import utils.DeliveryConstants;
import utils.PropertiesFileAccessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

public class DeliveryOrchestrator {

    private final Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
    private final IDeliveryProcessor deliveryProcessor;
    private final IFilesProcessor filesProcessor;
    private final Integer dronesSimultaneously;
    private final Logger logger = LogManager.getLogger(DeliveryOrchestrator.class);
    private List<String> droneIndications;
    private List<String> dronePositions;

    public DeliveryOrchestrator(IDeliveryProcessor deliveryProcessor, IFilesProcessor filesProcessor, Integer dronesSimultaneously) {
        this.deliveryProcessor = deliveryProcessor;
        this.filesProcessor = filesProcessor;
        this.dronesSimultaneously = dronesSimultaneously;
    }

    public void startDelivery() {
        final int droneCarryingCapacity = Integer.parseInt(properties.getProperty(DeliveryConstants.MEAL_CAPACITY));

        IntStream.range(0, dronesSimultaneously).forEach(i -> {

            int droneId = i + 1;

            retrieveDeliveryRoutes(formatFileName(properties.getProperty(DeliveryConstants.INPUT_FILE_NAME), droneId));

            if (droneIndications.isEmpty()) {
                logger.error("Delivery file for drone {} not found", String.valueOf(droneId));
            } else if (droneIndications.size() <= droneCarryingCapacity) {
                deliverOrders(droneId);
                generateDroneDeliveryReport(droneId);
            } else {
                logger.error("Max carrying capacity exceeded for Drone: {}", droneId);
            }
        });
    }

    private void retrieveDeliveryRoutes(String inputFileName) {
        droneIndications = filesProcessor.readFile(inputFileName);
    }

    private void deliverOrders(Integer droneId) {
        List<Drone> dronesCurrentPosition = deliveryProcessor
                .deliverMeal(droneIndications, droneId, Integer.parseInt(properties.getProperty(DeliveryConstants.MAX_BLOCKS)));

        dronePositions = formatDronesOutput(dronesCurrentPosition);
    }

    private void generateDroneDeliveryReport(Integer droneId) {
        try {
            filesProcessor.generateReport(dronePositions, formatFileName(properties.getProperty(DeliveryConstants.OUTPUT_FILE_NAME), droneId));
        } catch (IOException e) {
            logger.error("Error while generating report: {}", e.getMessage());
        }
    }

    private List<String> formatDronesOutput(List<Drone> dronesPositions) {
        List<String> result = new ArrayList<>();

        result.add(DeliveryConstants.REPORT_HEADER);

        dronesPositions.forEach(
                d -> result.add("("
                        .concat(d.getPositionX().toString())
                        .concat(", ")
                        .concat(d.getPositionY().toString())
                        .concat(") ")
                        .concat(d.getOrientation().getOrientationName()))
        );

        return result;
    }

    private String formatFileName(String fileType, Integer droneId) {
        String id = droneId < 10 ? "0".concat(droneId.toString()) : droneId.toString();
        return fileType.concat(id).concat(DeliveryConstants.REPORT_EXTENSION);
    }

}
