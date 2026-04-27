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

    options.addArguments("--disable-blink-features=AutomationControlled");
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-popup-blocking");
    options.addArguments("--start-maximized");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-infobars");
    options.addArguments("--disable-extensions");
    options.addArguments("--ignore-certificate-errors");

    options.setExperimentalOption("excludeSwitches",
        java.util.List.of("enable-automation"));
    options.setExperimentalOption("useAutomationExtension", false);

    if (headless) {
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
    }

    ChromeDriver driver = new ChromeDriver(options);

    // Remove webdriver flag
    driver.executeCdpCommand(
        "Page.addScriptToEvaluateOnNewDocument",
        java.util.Map.of(
            "source",
            "Object.defineProperty(navigator, 'webdriver', " +
            "{get: () => undefined})"
        )
    );

    logger.info("ChromeDriver created successfully");
    return driver;
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
        
     // Fix ERR_HTTP2_PROTOCOL_ERROR
        options.addArguments("--disable-http2");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches",
            java.util.List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        logger.info("EdgeDriver created successfully");
        return new EdgeDriver(options);
        
        
    }
    

    private DriverFactory() {}
}
