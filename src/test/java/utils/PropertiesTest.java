package utils;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PropertiesTest {

    @Test
    public void readPropertyTest() {
        Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
        assertFalse(properties.isEmpty());
        assertEquals(properties.get("test"), "myTest");
    }

    @Test
    public void propertiesFileNotFoundTest() {
        Properties properties = PropertiesFileAccessor.getProperties("test.properties");
        assertTrue(properties.isEmpty());
    }

    @Test
    public void propertyNotFoundTest() {
        Properties properties = PropertiesFileAccessor.getProperties(DeliveryConstants.APPLICATION_PROPERTIES);
        assertFalse(properties.isEmpty());
        assertNull(properties.get("test1"));
    }
}
