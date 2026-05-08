package io.github.nowshad.expedia.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import io.github.nowshad.expedia.components.FilterComponent;
import io.github.nowshad.expedia.components.FlightCardComponent;
import io.github.nowshad.expedia.components.SortComponent;
import io.github.nowshad.expedia.enums.WaitStrategy;

public class SearchResultsPage extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(SearchResultsPage.class);

    // Delegate card operations to component
    private final FlightCardComponent flightCards =
        new FlightCardComponent();

    private final FilterComponent filterComponent =
    	    new FilterComponent();
    
    private final SortComponent sortComponent =
    	    new SortComponent();
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
    
   

    /**
     * Returns FilterComponent for filter operations.
     */
    public FilterComponent filters() {
        return filterComponent;
    }

    /**
     * Returns current flight card count.
     * Used to verify filter changed results.
     */
    public int getCurrentResultCount() {
        return filterComponent.getCurrentResultCount();
    }

    /**
     * Selects non stop filter directly from page.
     */
    public SearchResultsPage filterByNonStop() {
        filterComponent.selectNonStop();
        return this;
    }

    /**
     * Selects one stop filter directly from page.
     */
    public SearchResultsPage filterByOneStop() {
        filterComponent.selectOneStop();
        return this;
    }

    /**
     * Filters by specific airline name.
     */
    public SearchResultsPage filterByAirline(String airlineName) {
        filterComponent.selectAirline(airlineName);
        return this;
    }

    /**
     * Clears all applied filters.
     */
    public SearchResultsPage clearAllFilters() {
        filterComponent.clearAllFilters();
        return this;
    }
    

    /**
     * Returns SortComponent for sort operations.
     */
    public SortComponent sort() {
        return sortComponent;
    }

    /**
     * Sorts results by Cheapest.
     */
    public SearchResultsPage sortByCheapest() {
        sortComponent.sortByCheapest();
        return this;
    }

    /**
     * Sorts by Non Stop First.
     */
    public SearchResultsPage sortByNonStopFirst() {
        sortComponent.sortByNonStopFirst();
        return this;
    }

    /**
     * Sorts by Early Departure.
     */
    public SearchResultsPage sortByEarlyDeparture() {
        sortComponent.sortByEarlyDeparture();
        return this;
    }

    /**
     * Sorts by Late Departure.
     */
    public SearchResultsPage sortByLateDeparture() {
        sortComponent.sortByLateDeparture();
        return this;
    }

    /**
     * Verifies prices sorted Low to High.
     */
    public boolean arePricesSortedLowToHigh() {
        return sortComponent.arePricesSortedLowToHigh();
    }

    /**
     * Returns first flight price after sort.
     */
    public int getFirstFlightPriceAfterSort() {
        return sortComponent.getFirstFlightPrice();
    }

    /**
     * Returns second flight price after sort.
     */
    public int getSecondFlightPriceAfterSort() {
        return sortComponent.getSecondFlightPrice();
    }

    /**
     * Verifies sort tab is active.
     */
    public boolean isSortActive(String tabName) {
        return sortComponent.isSortTabActive(tabName);
    }
    
    /**
     * Verifies Non Stop filter checkbox is selected.
     */
    public boolean isNonStopFilterActive() {
        return filterComponent.isNonStopSelected();
    }

    /**
     * Verifies One Stop filter checkbox is selected.
     */
    public boolean isOneStopFilterActive() {
        return filterComponent.isOneStopSelected();
    }
}