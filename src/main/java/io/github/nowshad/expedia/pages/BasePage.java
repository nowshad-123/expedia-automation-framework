package io.github.nowshad.expedia.pages;


import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.nowshad.expedia.config.ConfigReader;
import io.github.nowshad.expedia.driver.DriverManager;
import io.github.nowshad.expedia.enums.WaitStrategy;

public class BasePage {

    private static final Logger logger = LogManager.getLogger(BasePage.class);
    private final WebDriverWait wait;
    private final WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(ConfigReader.getInt("explicit.wait"))
        );
    }

    // ─────────────────────────────────────────
    //  CORE WAIT — all element fetching flows
    //  through this single method
    // ─────────────────────────────────────────

    protected WebElement waitForElement(By locator, WaitStrategy strategy) {
        logger.debug("Waiting for element: {} | Strategy: {}", locator, strategy);
        return switch (strategy) {
            case VISIBLE   -> wait.until(
                                 ExpectedConditions.visibilityOfElementLocated(locator));
            case CLICKABLE -> wait.until(
                                 ExpectedConditions.elementToBeClickable(locator));
            case PRESENCE  -> wait.until(
                                 ExpectedConditions.presenceOfElementLocated(locator));
            case VISIBLE_ALL -> throw new UnsupportedOperationException(
                                 "Use waitForElements() for VISIBLE_ALL strategy");
        };
    }

    protected List<WebElement> waitForElements(By locator) {
        logger.debug("Waiting for all elements: {}", locator);
        return wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ─────────────────────────────────────────
    //  CLICK UTILITIES
    // ─────────────────────────────────────────

    protected void click(By locator) {
        logger.info("Clicking element: {}", locator);
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForElement(locator, WaitStrategy.CLICKABLE).click();
                return; // success — exit
            } catch (StaleElementReferenceException e) {
                attempts++;
                logger.warn("Stale element on click attempt {}. " +
                            "Retrying: {}", attempts, locator);
            } catch (ElementClickInterceptedException e) {
                logger.warn("Click intercepted — trying JS click: {}",
                            locator);
                jsClick(locator);
                return;
            }
        }
        throw new RuntimeException(
            "Click failed after 3 attempts due to stale element: "
            + locator);
    }
    
    protected void clickMatchedCity(By locator, String city) {
    	logger.info("Clicking the matched city: {}", locator);
    	List<WebElement> options = waitForElements(locator);
    	
    	for (WebElement option : options) {
    		if (option.getText().contains(city)) {
    	    	logger.info("City Name: ", option.getText());
    	        option.click();
    	        break;
    	    }
    	}
    }

    protected void jsClick(By locator) {
        logger.info("JS click on element: {}", locator);
        WebElement element = waitForElement(locator, WaitStrategy.PRESENCE);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ─────────────────────────────────────────
    //  TYPE / INPUT UTILITIES
    // ─────────────────────────────────────────

    protected void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement element = waitForElement(locator, WaitStrategy.CLICKABLE);
        element.clear();
        element.sendKeys(text);
    }

    protected void clearAndType(By locator, String text) {
        logger.info("Clear and typing '{}' into element: {}", text, locator);
        WebElement element = waitForElement(locator, WaitStrategy.CLICKABLE);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    protected void typeWithoutClear(By locator, String text) {
        logger.info("Typing without clear '{}' into: {}", text, locator);
        waitForElement(locator, WaitStrategy.CLICKABLE).sendKeys(text);
    }

    // ─────────────────────────────────────────
    //  READ UTILITIES
    // ─────────────────────────────────────────

    protected String getText(By locator) {
        String text = waitForElement(locator, WaitStrategy.VISIBLE).getText();
        logger.info("Got text '{}' from element: {}", text, locator);
        return text;
    }

    protected String getAttribute(By locator, String attribute) {
        String value = waitForElement(locator, WaitStrategy.PRESENCE)
                           .getAttribute(attribute);
        logger.info("Got attribute '{}' = '{}' from: {}", attribute, value, locator);
        return value;
    }

    // ─────────────────────────────────────────
    //  VISIBILITY CHECKS
    // ─────────────────────────────────────────

    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator, WaitStrategy.VISIBLE).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.warn("Element not visible: {}", locator);
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return waitForElement(locator, WaitStrategy.VISIBLE).isEnabled();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.warn("Element not enabled: {}", locator);
            return false;
        }
    }

    // ─────────────────────────────────────────
    //  SCROLL UTILITIES
    // ─────────────────────────────────────────

    protected void scrollIntoView(By locator) {
        logger.info("Scrolling into view: {}", locator);
        WebElement element = getDriver().findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
        	    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
        	    element
        	);
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver)
            .executeScript("window.scrollTo(0, 0);");
        logger.info("Scrolled to top of page");
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver)
            .executeScript("window.scrollTo(0, document.body.scrollHeight);");
        logger.info("Scrolled to bottom of page");
    }

    // ─────────────────────────────────────────
    //  HOVER
    // ─────────────────────────────────────────

    protected void hover(By locator) {
        logger.info("Hovering over element: {}", locator);
        WebElement element = waitForElement(locator, WaitStrategy.VISIBLE);
        new Actions(driver).moveToElement(element).perform();
    }

    // ─────────────────────────────────────────
    //  PAGE UTILITIES
    // ─────────────────────────────────────────

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    public void waitForPageLoad() {
        logger.info("Waiting for page to fully load...");
        wait.until(webDriver ->
            ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete")
        );
        logger.info("Page fully loaded");
    }

    // ─────────────────────────────────────────
    //  STALE ELEMENT RETRY
    // ─────────────────────────────────────────

    protected WebElement getElementWithRetry(By locator, WaitStrategy strategy) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return waitForElement(locator, strategy);
            } catch (StaleElementReferenceException e) {
                logger.warn("Stale element on attempt {}. Retrying: {}", attempts + 1, locator);
                attempts++;
            }
        }
        throw new RuntimeException("Element still stale after 3 attempts: " + locator);
    }

    // ─────────────────────────────────────────
    //  EXPOSE DRIVER AND WAIT FOR SUBCLASSES
    // ─────────────────────────────────────────

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        return wait;
    }
}
