package io.github.nowshad.expedia.pages;



import io.github.nowshad.expedia.enums.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(SearchResultsPage.class);

    // ─────────────────────────────────────────
    //  LOCATORS
    // ─────────────────────────────────────────

    // Results container — visible when results load
    private final By resultsContainer = By.xpath(
        "//div[contains(@class,'listingCard')]" +
        " | //div[contains(@class,'flight-card')]" +
        " | //div[contains(@class,'airlineRow')]"
    );

    // Individual flight result cards
    private final By flightCards = By.xpath(
        "//div[contains(@class,'listingCard')]"
    );

    // Sort options
    private final By sortByPrice = By.xpath(
        "//*[contains(text(),'Price')]" +
        "[contains(@class,'sort')]"
    );

    // Results count text
    private final By resultsCount = By.xpath(
        "//*[contains(@class,'resultsCount')]" +
        " | //*[contains(text(),'flights found')]"
    );

    // Loading spinner — wait for it to disappear
    private final By loadingSpinner = By.xpath(
        "//div[contains(@class,'loading')]" +
        " | //div[contains(@class,'spinner')]"
    );

    // ─────────────────────────────────────────
    //  VERIFICATIONS
    // ─────────────────────────────────────────

    /**
     * Verifies search results page loaded correctly.
     * Checks URL contains flight/search parameters.
     */
    public boolean isResultsPageLoaded() {
        String url = getCurrentUrl();
        logger.info("Results page URL: {}", url);
        return url.contains("flight") &&
               url.contains("search");
    }

    /**
     * Waits for flight results to load.
     * MakeMyTrip shows a spinner while loading.
     */
    public SearchResultsPage waitForResultsToLoad() {
        logger.info("Waiting for flight results to load...");
        waitForPageLoad();
        try {
            // Wait for at least one result card
            waitForElement(resultsContainer,
                WaitStrategy.VISIBLE);
            logger.info("Flight results loaded");
        } catch (Exception e) {
            logger.warn("Results container not found " +
                "— checking URL instead");
        }
        return this;
    }

    /**
     * Returns number of flight cards on results page.
     */
    public int getFlightCardCount() {
        try {
            int count = waitForElements(flightCards).size();
            logger.info("Flight cards found: {}", count);
            return count;
        } catch (Exception e) {
            logger.warn("Could not count flight cards: {}",
                e.getMessage());
            return 0;
        }
    }

    /**
     * Verifies URL contains expected route.
     * e.g. DEL-BOM for Delhi to Mumbai
     */
    public boolean isCorrectRoute(
            String originCode, String destCode) {
        String url = getCurrentUrl();
        boolean correct = url.contains(originCode) &&
                         url.contains(destCode);
        logger.info("Route check {}-{}: {}",
            originCode, destCode, correct);
        return correct;
    }

    /**
     * Returns full search URL for logging/reporting.
     */
    public String getSearchUrl() {
        return getCurrentUrl();
    }
}
