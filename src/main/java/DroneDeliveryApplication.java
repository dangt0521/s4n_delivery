import delivery.DeliveryOrchestrator;
import delivery.process.DeliveryProcessor;
import reports.FilesProcessor;
import utils.DeliveryConstants;
import utils.PropertiesFileAccessor;

import java.util.Properties;

public class DroneDeliveryApplication {

    public static void main(String[] args) {
        Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
        int dronesSimultaneously = Integer.parseInt(properties.getProperty(DeliveryConstants.DRONES_SIMULTANEOUSLY));

        DeliveryOrchestrator deliveryOrchestrator = new DeliveryOrchestrator(new DeliveryProcessor(), new FilesProcessor(), dronesSimultaneously);
        deliveryOrchestrator.startDelivery();
    }
}
