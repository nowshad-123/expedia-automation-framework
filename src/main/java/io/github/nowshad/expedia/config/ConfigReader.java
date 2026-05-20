package io.github.nowshad.expedia.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties = new Properties();
            properties.load(fis);
            logger.info("Configuration loaded successfully from: {}", CONFIG_PATH);
        } catch (IOException e) {
            logger.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Cannot load configuration file. Check path: " + CONFIG_PATH, e);
        }
    }

   public static String get(String key) {
    // Check system property first
    // Allows -Dkey=value override from CLI and CI
    String sysProp = System.getProperty(key);
    if (sysProp != null && !sysProp.isEmpty()) {
        logger.info("Using system property " +
            "for key '{}': {}", key, sysProp);
        return sysProp;
    }

    // Fall back to properties file
    String value = properties.getProperty(key);
    if (value == null) {
        logger.warn("Property '{}' not found",
            key);
    }
    return value;
}
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        String value = get(key);
        return Boolean.parseBoolean(value);
    }

    // Prevent instantiation
    private ConfigReader() {}
}
