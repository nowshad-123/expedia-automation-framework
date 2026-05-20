package io.github.nowshad.expedia.hooks;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.nowshad.expedia.config.ConfigReader;
import io.github.nowshad.expedia.driver.DriverManager;

public class Hooks {

	private static final Logger logger = LogManager.getLogger(Hooks.class);
	private long scenarioStartTime;

	@Before
	public void setUp(Scenario scenario) {
		scenarioStartTime = System.currentTimeMillis();
		logger.info("===== Starting: {} =====", scenario.getName());
		DriverManager.initDriver();
		navigateWithRetry(ConfigReader.get("base.url"));
	}

	private void navigateWithRetry(String url) {
		int attempts = 0;
		while (attempts < 3) {
			try {
				DriverManager.getDriver().get(url);
				// Verify page loaded — not error page
				String currentUrl = DriverManager.getDriver().getCurrentUrl();
				if (!currentUrl.contains("neterror") && !currentUrl.contains("about:")) {
					logger.info("Navigated to: {}", url);
					return;
				}
				logger.warn("Error page detected on " + "attempt {}. Retrying...", attempts + 1);
			} catch (Exception e) {
				logger.warn("Navigation failed attempt {}: {}", attempts + 1, e.getMessage());
			}
			attempts++;
			try {
				Thread.sleep(3000); // wait before retry
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		throw new RuntimeException("Failed to navigate to " + url + " after 3 attempts. "
				+ "Check network connection and MMT availability.");
	}

	@After
	public void tearDown(Scenario scenario) {
		long duration = System.currentTimeMillis() - scenarioStartTime;

		if (scenario.isFailed()) {
			logger.error("FAILED: {} | Time: {}s", scenario.getName(), duration / 1000);
			takeScreenshot(scenario);
		} else {
			logger.info("PASSED: {} | Time: {}s", scenario.getName(), duration / 1000);
		}

		DriverManager.quitDriver();

		// Cooldown between scenarios
		// Prevents MMT rate limiting on full suite runs
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void takeScreenshot(Scenario scenario) {
		try {
			byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
			logger.info("Screenshot captured for failed scenario: {}", scenario.getName());
		} catch (Exception e) {
			logger.error("Failed to capture screenshot: {}", e.getMessage());
		}
	}
}
