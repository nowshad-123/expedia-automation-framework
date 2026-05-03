package io.github.nowshad.expedia.pages;

import io.github.nowshad.expedia.components.FlightCardComponent;
import io.github.nowshad.expedia.enums.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(SearchResultsPage.class);

    // Delegate card operations to component
    private final FlightCardComponent flightCards =
        new FlightCardComponent();

    // ─────────────────────────────────────────
    //  LOCATORS — confirmed from DevTools
    // ─────────────────────────────────────────

    private final By resultsContainer = By.xpath(
        "//div[contains(@class,'appendBottom5')]"
    );

    private final By loadingSpinner = By.xpath(
        "//div[contains(@class,'loading')]" +
        " | //div[contains(@class,'spinner')]"
    );

    private final By noResultsMessage = By.xpath(
        "//*[contains(text(),'No flights')]" +
        " | //*[contains(@class,'noResult')]"
    );

    private final By filterSection = By.xpath(
        "//div[contains(@class,'filter')]"
    );

    // ─────────────────────────────────────────
    //  PAGE LOAD VERIFICATION
    // ─────────────────────────────────────────

    /**
     * Verifies results page loaded.
     * Checks URL contains flight search params.
     */
    public boolean isResultsPageLoaded() {
        String url = getCurrentUrl();
        logger.info("Results URL: {}", url);
        return url.contains("flight") &&
               url.contains("search");
    }

    /**
     * Waits for results to fully load.
     * Handles spinner then waits for cards.
     */
    public SearchResultsPage waitForResultsToLoad() {
        logger.info("Waiting for results to load...");
        waitForPageLoad();

        // Wait for spinner to appear and pass
        try {
            waitForElement(loadingSpinner,
                WaitStrategy.VISIBLE);
            logger.info("Spinner detected — waiting...");
        } catch (Exception e) {
            logger.info("No spinner detected");
        }

        // Wait for flight cards to appear
        try {
            waitForElement(resultsContainer,
                WaitStrategy.VISIBLE);
            logger.info("Flight cards visible");
        } catch (Exception e) {
            logger.warn("Cards not found — URL: {}",
                getCurrentUrl());
        }
        return this;
    }

    /**
     * Verifies URL contains correct IATA codes.
     */
    public boolean isCorrectRoute(
            String originCode, String destCode) {
        String url = getCurrentUrl();
        boolean correct =
            url.contains(originCode) &&
            url.contains(destCode);
        logger.info("Route {}-{} in URL: {}",
            originCode, destCode, correct);
        return correct;
    }

    // ─────────────────────────────────────────
    //  RESULTS VALIDATION
    // ─────────────────────────────────────────

    /**
     * Returns count of visible flight cards.
     * MMT loads 3-4 cards on first load.
     */
    public int getResultCount() {
        int count = flightCards.getFlightCardCount();
        logger.info("Total flight cards: {}", count);
        return count;
    }

    /**
     * Verifies at least 1 result present.
     */
    public boolean hasResults() {
        int count = getResultCount();
        logger.info("Has results: {}", count > 0);
        return count > 0;
    }

    /**
     * Validates all cards have valid prices.
     */
    public boolean allResultsHaveValidPrices() {
        return flightCards.allCardsHaveValidPrices();
    }

    /**
     * Validates all cards have airline names.
     */
    public boolean allResultsHaveAirlineNames() {
        return flightCards.allCardsHaveAirlineNames();
    }

    /**
     * Returns numeric price of first card.
     */
    public int getFirstFlightPrice() {
        return flightCards.getNumericPrice(0);
    }

    /**
     * Returns airline name of first card.
     */
    public String getFirstFlightAirline() {
        return flightCards.getAirlineNameByIndex(0);
    }

    /**
     * Returns stops info of first card.
     * Fixed: was getStopsInfo → getStopsByIndex
     */
    public String getFirstFlightStops() {
        return flightCards.getStopsByIndex(0);
    }

    /**
     * Verifies no-results message absent.
     */
    public boolean isNoResultsMessageAbsent() {
        boolean absent = !isDisplayed(noResultsMessage);
        logger.info("No-results message absent: {}",
            absent);
        return absent;
    }

    /**
     * Returns full URL for assertions.
     */
    public String getSearchUrl() {
        return getCurrentUrl();
    }

    /**
     * Verifies filter panel is visible.
     */
    public boolean isFilterSectionVisible() {
        return isDisplayed(filterSection);
    }
}