package io.github.nowshad.expedia.driver;

import io.github.nowshad.expedia.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static WebDriver createDriver(String browser) {
        boolean headless = ConfigReader.getBoolean("headless");
        logger.info("Creating driver for browser: {} | headless: {}", browser, headless);

        return switch (browser.toLowerCase().trim()) {
            case "chrome"   -> createChromeDriver(headless);
            case "firefox"  -> createFirefoxDriver(headless);
            case "edge"     -> createEdgeDriver(headless);
            default -> {
                logger.warn("Unknown browser '{}'. Defaulting to Chrome.", browser);
                yield createChromeDriver(headless);
            }
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        logger.info("ChromeDriver created successfully");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        logger.info("FirefoxDriver created successfully");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        logger.info("EdgeDriver created successfully");
        return new EdgeDriver(options);
    }

    private DriverFactory() {}
}
