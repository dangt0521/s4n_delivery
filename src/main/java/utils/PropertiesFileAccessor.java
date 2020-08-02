package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reports.FilesProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileAccessor {

    private static final Logger logger = LogManager.getLogger(FilesProcessor.class);

    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = PropertiesFileAccessor.class.getClassLoader().getResourceAsStream(fileName)) {

            if (input != null) {
                properties.load(input);
            } else {
                logger.error("Unable to find {} file", fileName);
            }

            return properties;
        } catch (IOException e) {
            logger.error("Error while reading file {}}: {}", fileName, e.getMessage());
        }

        return properties;
    }
}
