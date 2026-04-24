package io.github.nowshad.expedia.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import io.github.nowshad.expedia.config.ConfigReader;

public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    // ThreadLocal ensures each thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initDriver() {
        if (getDriver() != null) {
            logger.warn("Driver already initialized for this thread. Skipping.");
            return;
        }
        String browser = ConfigReader.get("browser", "chrome");
        WebDriver driver = DriverFactory.createDriver(browser);

        // Set timeouts from config
        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        driver.manage().timeouts()
              .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
        logger.info("Driver initialized and stored in ThreadLocal for thread: {}",
                    Thread.currentThread().getName());
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("Driver quit and removed from ThreadLocal for thread: {}",
                        Thread.currentThread().getName());
        } else {
            logger.warn("quitDriver called but no driver found in ThreadLocal.");
        }
    }

    private DriverManager() {}
}