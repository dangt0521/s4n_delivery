package delivery;

import delivery.parameters.CardinalPoint;
import delivery.parameters.Drone;
import delivery.process.DeliveryProcessor;
import org.junit.Before;
import org.junit.Test;
import utils.DeliveryConstants;
import utils.PropertiesFileAccessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class DeliveryProcessorTest {

    private DeliveryProcessor processor;
    private Integer displacementCapacity;

    @Before
    public void init() {
        processor = new DeliveryProcessor();
        Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
        displacementCapacity = Integer.parseInt(properties.getProperty(DeliveryConstants.MAX_BLOCKS));
    }

    @Test
    public void deliverOrdersTest() {
        List<String> orders = Arrays.asList("AAAAIAA", "DDDAIAD", "AAIADAD");
        List<Drone> drones = processor.deliverMeal(orders, 0, displacementCapacity);

        assertEquals(drones.size(), 3);

        assertEquals(drones.get(0).getOrientation(), CardinalPoint.WEST);
        assertEquals(drones.get(0).getPositionX(), Integer.valueOf(-2));
        assertEquals(drones.get(0).getPositionY(), Integer.valueOf(4));

        assertEquals(drones.get(1).getOrientation(), CardinalPoint.SOUTH);
        assertEquals(drones.get(1).getPositionX(), Integer.valueOf(-1));
        assertEquals(drones.get(1).getPositionY(), Integer.valueOf(3));

        assertEquals(drones.get(2).getOrientation(), CardinalPoint.WEST);
        assertEquals(drones.get(2).getPositionX(), Integer.valueOf(0));
        assertEquals(drones.get(2).getPositionY(), Integer.valueOf(0));
    }

    @Test
    public void deliverOneMealTest() {
        List<String> orders = Collections.singletonList("AAAAIAA");
        List<Drone> drones = processor.deliverMeal(orders, 1, displacementCapacity);

        assertEquals(drones.size(), 1);

        assertEquals(drones.get(0).getOrientation(), CardinalPoint.WEST);
        assertEquals(drones.get(0).getPositionX(), Integer.valueOf(-2));
        assertEquals(drones.get(0).getPositionY(), Integer.valueOf(4));
    }

    @Test
    public void deliverOutOfRangeTest() {
        List<String> orders = Arrays.asList("AAAAAAD", "IAAAAAI", "AAIAAAD");
        List<Drone> drones = processor.deliverMeal(orders, 2, displacementCapacity);

        assertEquals(drones.size(), 2);

        assertEquals(drones.get(0).getOrientation(), CardinalPoint.EAST);
        assertEquals(drones.get(0).getPositionX(), Integer.valueOf(0));
        assertEquals(drones.get(0).getPositionY(), Integer.valueOf(6));

        assertEquals(drones.get(1).getOrientation(), CardinalPoint.WEST);
        assertEquals(drones.get(1).getPositionX(), Integer.valueOf(-2));
        assertEquals(drones.get(1).getPositionY(), Integer.valueOf(8));
    }

    @Test
    public void deliverWhenWrongIndicationsTest() {
        List<String> orders = Arrays.asList("AAAAIAA", "HIREME!", "AAIIIID");
        List<Drone> drones = processor.deliverMeal(orders, 3, displacementCapacity);

        assertEquals(drones.size(), 2);

        assertEquals(drones.get(0).getOrientation(), CardinalPoint.WEST);
        assertEquals(drones.get(0).getPositionX(), Integer.valueOf(-2));
        assertEquals(drones.get(0).getPositionY(), Integer.valueOf(4));

        assertEquals(drones.get(1).getOrientation(), CardinalPoint.NORTH);
        assertEquals(drones.get(1).getPositionX(), Integer.valueOf(-4));
        assertEquals(drones.get(1).getPositionY(), Integer.valueOf(4));
    }
}
