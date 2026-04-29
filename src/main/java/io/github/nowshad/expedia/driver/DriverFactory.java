package io.github.nowshad.expedia.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.nowshad.expedia.config.ConfigReader;

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

    options.addArguments("--incognito");
//    options.addArguments("--disable-cache");

    // Anti-detection
//    options.addArguments("--disable-blink-features=AutomationControlled");
//    options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120 Safari/537.36");
//    options.addArguments("--user-data-dir=/tmp/selenium-profile-" + System.currentTimeMillis());
//    options.setExperimentalOption("excludeSwitches",
//        java.util.List.of("enable-automation"));
//    options.setExperimentalOption("useAutomationExtension", false);
//
//    // Stability
//    options.addArguments("--disable-extensions");
//    options.addArguments("--no-sandbox");
//    options.addArguments("--disable-dev-shm-usage");
//    options.addArguments("--disable-notifications");
//    options.addArguments("--disable-popup-blocking");
//    options.addArguments("--ignore-certificate-errors");

    // 🚨 VERY IMPORTANT
//    options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

    if (headless) {
        // ⚠️ Try avoiding headless for this site
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
    } else {
        options.addArguments("--start-maximized");
    }

    ChromeDriver driver = new ChromeDriver(options);

    // Remove webdriver flag
    driver.executeCdpCommand(
        "Page.addScriptToEvaluateOnNewDocument",
        java.util.Map.of(
            "source",
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
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
        options.addArguments("--incognito");
//        options.addArguments("--disable-http2");
//        options.addArguments("--disable-notifications");
//        options.addArguments("--disable-popup-blocking");
//        options.addArguments("--start-maximized");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.setExperimentalOption("excludeSwitches",
//            java.util.List.of("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }else {
            options.addArguments("--start-maximized");
        }

        logger.info("EdgeDriver created successfully");
        return new EdgeDriver(options);
        
        
    }
    

    private DriverFactory() {}
}
