package io.github.nowshad.expedia.hooks;

import io.github.nowshad.expedia.config.ConfigReader;
import io.github.nowshad.expedia.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("===== Starting Scenario: {} =====", scenario.getName());
        DriverManager.initDriver();
        DriverManager.getDriver().get(ConfigReader.get("base.url"));
        logger.info("Navigated to base URL: {}", ConfigReader.get("base.url"));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: {}", scenario.getName());
            takeScreenshot(scenario);
        } else {
            logger.info("Scenario PASSED: {}", scenario.getName());
        }
        DriverManager.quitDriver();
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            logger.info("Screenshot captured for failed scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }
}
