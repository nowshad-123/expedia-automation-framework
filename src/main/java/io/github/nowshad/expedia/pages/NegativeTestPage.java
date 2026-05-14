package io.github.nowshad.expedia.pages;

import io.github.nowshad.expedia.enums.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class NegativeTestPage extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(NegativeTestPage.class);

    // ─────────────────────────────────────────
    //  LOCATORS — confirmed from DevTools
    // ─────────────────────────────────────────

    // Same origin/destination error message
    // Appears immediately after selecting same city
    private final By sameAirportError = By.xpath(
        "//span[@class='redText fltErrorMsgText']"
    );

    // Search button — disabled when same city
    private final By searchButton = By.cssSelector(
        "a.primaryBtn"
    );

    // No results page — error title
    private final By noFlightsTitle = By.xpath(
        "//p[@class='error-title']"
    );

    // No results page — error subtitle
    private final By noFlightsSubtitle = By.xpath(
        "//p[@class='error-subtitle']"
    );

    // Go Back button on no results page
    private final By goBackButton = By.xpath(
        "//a[contains(@class,'buttonPrimary')]"
    );

    // ─────────────────────────────────────────
    //  SAME AIRPORT ERROR VALIDATION
    // ─────────────────────────────────────────

    /**
     * Checks if same airport error is displayed.
     * Appears immediately after selecting same
     * city in both From and To fields.
     */
    public boolean isSameAirportErrorDisplayed() {
        boolean displayed =
            isDisplayed(sameAirportError);
        logger.info("Same airport error displayed: {}",
            displayed);
        return displayed;
    }

    /**
     * Returns the exact error message text.
     * Expected: "From & To airports cannot
     * be the same"
     */
    public String getSameAirportErrorText() {
        try {
            String text = getText(sameAirportError);
            logger.info("Error message text: {}", text);
            return text;
        } catch (Exception e) {
            logger.warn("Could not get error text: {}",
                e.getMessage());
            return "";
        }
    }

    
    // ─────────────────────────────────────────
    //  NO RESULTS VALIDATION
    // ─────────────────────────────────────────

    /**
     * Checks if no flights found page is shown.
     * Appears after searching a route with
     * no available flights.
     */
    public boolean isNoFlightsPageDisplayed() {
        boolean displayed =
            isDisplayed(noFlightsTitle);
        logger.info("No flights page displayed: {}",
            displayed);
        return displayed;
    }

    /**
     * Returns the no flights title text.
     * Expected: "Sorry, Flights Not Found"
     */
    public String getNoFlightsTitleText() {
        try {
            String text = getText(noFlightsTitle);
            logger.info("No flights title: {}", text);
            return text;
        } catch (Exception e) {
            logger.warn("Could not get title: {}",
                e.getMessage());
            return "";
        }
    }

    /**
     * Returns the no flights subtitle text.
     */
    public String getNoFlightsSubtitleText() {
        try {
            String text = getText(noFlightsSubtitle);
            logger.info("No flights subtitle: {}",
                text);
            return text;
        } catch (Exception e) {
            logger.warn("Could not get subtitle: {}",
                e.getMessage());
            return "";
        }
    }

    /**
     * Checks if Go Back button is visible
     * on no results page.
     */
    public boolean isGoBackButtonVisible() {
        boolean visible = isDisplayed(goBackButton);
        logger.info("Go Back button visible: {}",
            visible);
        return visible;
    }

    /**
     * Clicks Go Back button to return
     * to search form.
     */
    public void clickGoBack() {
        click(goBackButton);
        logger.info("Clicked Go Back button");
        waitForPageLoad();
    }

    /**
     * Waits for no results page to load.
     */
    public NegativeTestPage waitForNoResultsPage() {
        logger.info("Waiting for no results page...");
        waitForPageLoad();
        try {
            waitForElement(noFlightsTitle,
                WaitStrategy.VISIBLE);
            logger.info("No results page loaded");
        } catch (Exception e) {
            logger.warn("No results page timeout: {}",
                e.getMessage());
        }
        return this;
    }
}