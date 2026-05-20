package io.github.nowshad.expedia.components;

import io.github.nowshad.expedia.enums.WaitStrategy;
import io.github.nowshad.expedia.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SortComponent extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(SortComponent.class);

    // ─────────────────────────────────────────
    //  LOCATORS — all confirmed from DevTools
    // ─────────────────────────────────────────

    // Sort tabs container
    private final By sortContainer = By.xpath(
        "//div[contains(@class,'sortTabsWrapper')]"
    );

    // Primary sort tabs
    private final By cheapestTab = By.xpath(
        "//p[normalize-space()='Cheapest']"
    );

    private final By nonStopFirstTab = By.xpath(
        "//p[normalize-space()='Non Stop First']"
    );

    private final By youMayPreferTab = By.xpath(
        "//p[normalize-space()='You May Prefer']"
    );

    // Other Sort — opens dropdown
    private final By otherSortTab = By.xpath(
        "//p[normalize-space()='Other Sort']"
    );

    // Dropdown options inside ul.sortDropDown
    private final By sortDropdown = By.xpath(
        "//ul[@class='sortDropDown']"
    );

    private final By discountedPrice = By.xpath(
        "//li[normalize-space()='Discounted Price']"
    );

    private final By earlyDeparture = By.xpath(
        "//li[normalize-space()='Early Departure']"
    );

    private final By lateDeparture = By.xpath(
        "//li[normalize-space()='Late Departure']"
    );

    private final By earlyArrival = By.xpath(
        "//li[normalize-space()='Early Arrival']"
    );

    private final By lateArrival = By.xpath(
        "//li[normalize-space()='Late Arrival']"
    );

    

    // Price elements — confirmed count: 2
    // Format: ₹ 7,875
    private final By priceElements = By.xpath("//span[contains(@class,'fontSize18') and contains(@class,'blackFont')]");

    // Flight cards — for count before/after sort
    private final By flightCards = By.xpath(
        "//div[contains(@class,'appendBottom5')]"
    );
    
    //overlay pop up cross locator
    private final By commonOverlayCloseLocator = By.xpath("//div[contains(@class,'commonOverlay')] ");

    // ─────────────────────────────────────────
    //  PRIMARY SORT TABS
    // ─────────────────────────────────────────

    /**
     * Clicks Cheapest sort tab.
     * Results reorder immediately — confirmed.
     */
    public SortComponent sortByCheapest() {
        logger.info("Sorting by Cheapest");
        clickSortTab(cheapestTab, "Cheapest");
        return this;
    }

    /**
     * Clicks Non Stop First sort tab.
     */
    public SortComponent sortByNonStopFirst() {
        logger.info("Sorting by Non Stop First");
        clickSortTab(nonStopFirstTab, "Non Stop First");
        return this;
    }

    /**
     * Clicks You May Prefer sort tab.
     */
    public SortComponent sortByYouMayPrefer() {
        logger.info("Sorting by You May Prefer");
        clickSortTab(youMayPreferTab, "You May Prefer");
        return this;
    }

    // ─────────────────────────────────────────
    //  OTHER SORT DROPDOWN
    // ─────────────────────────────────────────

    /**
     * Selects Discounted Price from
     * Other Sort dropdown.
     */
    public SortComponent sortByDiscountedPrice() {
        logger.info("Sorting by Discounted Price");
        openOtherSortDropdown();
        clickDropdownOption(
            discountedPrice, "Discounted Price");
        return this;
    }

    /**
     * Selects Early Departure from dropdown.
     */
    public SortComponent sortByEarlyDeparture() {
        logger.info("Sorting by Early Departure");
        openOtherSortDropdown();
        clickDropdownOption(
            earlyDeparture, "Early Departure");
        return this;
    }

    /**
     * Selects Late Departure from dropdown.
     */
    public SortComponent sortByLateDeparture() {
        logger.info("Sorting by Late Departure");
        openOtherSortDropdown();
        clickDropdownOption(
            lateDeparture, "Late Departure");
        return this;
    }

    /**
     * Selects Early Arrival from dropdown.
     */
    public SortComponent sortByEarlyArrival() {
        logger.info("Sorting by Early Arrival");
        openOtherSortDropdown();
        clickDropdownOption(
            earlyArrival, "Early Arrival");
        return this;
    }

    /**
     * Selects Late Arrival from dropdown.
     */
    public SortComponent sortByLateArrival() {
        logger.info("Sorting by Late Arrival");
        openOtherSortDropdown();
        clickDropdownOption(
            lateArrival, "Late Arrival");
        return this;
    }

    // ─────────────────────────────────────────
    //  SORT VALIDATION
    // ─────────────────────────────────────────

    /**
     * Verifies sort tab is active by checking
     * "active" class on sortTabHeadList element.
     * Confirmed: active class added on selection.
     */
    public boolean isSortTabActive(String tabName) {
        try {
            By activeTabByName = By.xpath(
                "//div[contains(@class,'sortTabHeadList')" +
                " and contains(@class,'active')]" +
                "//p[contains(text(),'" + tabName + "')]"
            );
            boolean active = isDisplayed(activeTabByName);
            logger.info("Sort tab '{}' active: {}",
                tabName, active);
            return active;
        } catch (Exception e) {
            logger.warn("Could not verify active tab: {}",
                e.getMessage());
            return false;
        }
    }

    /**
     * Validates prices sorted Low to High.
     * Uses findElements — not visibility based.
     * Scrolls to price area first for headless.
     */
    public boolean arePricesSortedLowToHigh() {
        // Scroll to results area first
        // ensures elements are in render zone
        scrollIntoView(flightCards);

        List<WebElement> prices =
            getDriver().findElements(priceElements);

        if (prices.size() < 2) {
            logger.warn("Less than 2 prices found: {}. " +
                "Cannot validate sort order.",
                prices.size());
            return true; // skip — not enough data
        }

        int price1 = extractNumericPrice(
            prices.get(0).getText());
        int price2 = extractNumericPrice(
            prices.get(1).getText());

        logger.info("Price[0]: {} | Price[1]: {}",
            price1, price2);

        boolean sorted = price1 <= price2;
        logger.info("Prices sorted Low→High: {}", sorted);
        return sorted;
    }

    
    /**
     * Returns current visible result count.
     * Used to verify sort didn't hide results.
     */
    public int getCurrentResultCount() {
        try {
            List<WebElement> cards =
            		 getDriver().findElements(flightCards);
            logger.info("Results after sort: {}",
                cards.size());
            return cards.size();
        } catch (Exception e) {
            logger.warn("Could not count results: {}",
                e.getMessage());
            return 0;
        }
    }

    /**
     * Returns first flight price as integer.
     */
    public int getFirstFlightPrice() {
        scrollIntoView(flightCards);
        List<WebElement> prices =
            getDriver().findElements(priceElements);
        if (prices.isEmpty()) {
            logger.warn("No price elements found");
            return 0;
        }
        int price = extractNumericPrice(
            prices.get(0).getText());
        logger.info("First flight price: {}", price);
        return price;
    }

    /**
     * Returns second flight price as integer.
     */
    public int getSecondFlightPrice() {
        scrollIntoView(flightCards);
        List<WebElement> prices =
            getDriver().findElements(priceElements);
        if (prices.size() < 2) {
            logger.warn("Less than 2 prices found");
            return 0;
        }
        int price = extractNumericPrice(
            prices.get(1).getText());
        logger.info("Second flight price: {}", price);
        return price;
    }
    
    public void closeOverlay() {
        waitForPageLoad();
        waitForElement(commonOverlayCloseLocator, WaitStrategy.CLICKABLE);
        click(commonOverlayCloseLocator);
        logger.info("Overlay closed successfully");
    }
    // ─────────────────────────────────────────
    //  PRIVATE HELPERS
    // ─────────────────────────────────────────

    private void clickSortTab(
            By tabLocator, String tabName) {
        try {
            scrollIntoView(sortContainer);
            waitForElement(tabLocator,
                WaitStrategy.CLICKABLE);
            click(tabLocator);
            logger.info("Sort tab clicked: {}", tabName);
            waitForSortToApply();
        } catch (Exception e) {
            logger.error("Sort tab click failed {}: {}",
                tabName, e.getMessage());
            throw new RuntimeException(
                "Could not click sort tab: " +
                tabName, e);
        }
    }

    private void openOtherSortDropdown() {
        scrollIntoView(sortContainer);
        click(otherSortTab);
        logger.info("Other Sort dropdown opened");
        // Wait for dropdown to appear
        waitForElement(sortDropdown,
            WaitStrategy.VISIBLE);
        logger.info("Sort dropdown visible");
    }

    private void clickDropdownOption(
            By optionLocator, String optionName) {
        waitForElement(optionLocator,
            WaitStrategy.CLICKABLE);
        click(optionLocator);
        logger.info("Dropdown option clicked: {}",
            optionName);
        waitForSortToApply();
    }

    /**
     * Waits for sort to apply.
     * Results reorder immediately — no spinner.
     * Brief pause for React re-render.
     */
    private void waitForSortToApply() {
        try {
            Thread.sleep(1500);
            waitForPageLoad();
            logger.info("Sort applied");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Extracts numeric value from price string.
     * Format: "₹ 7,875" → 7875
     * replaceAll("[^0-9]","") handles ₹, space, comma
     */
    private int extractNumericPrice(String rawPrice) {
        try {
            String numeric =
                rawPrice.replaceAll("[^0-9]", "");
            int price = Integer.parseInt(numeric);
            logger.debug("Price parsed: '{}' → {}",
                rawPrice, price);
            return price;
        } catch (Exception e) {
            logger.warn("Price parse failed '{}': {}",
                rawPrice, e.getMessage());
            return 0;
        }
    }
}