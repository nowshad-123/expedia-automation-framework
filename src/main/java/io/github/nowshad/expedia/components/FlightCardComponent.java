package io.github.nowshad.expedia.components;

import io.github.nowshad.expedia.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FlightCardComponent extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(FlightCardComponent.class);

    // ─────────────────────────────────────────
    //  LOCATORS — confirmed from DevTools
    // ─────────────────────────────────────────

    // All flight cards on results page
    // Confirmed locator: appendBottom5
    private final By allFlightCards = By.xpath(
        "//div[contains(@class,'appendBottom5')]"
    );

    // Airline name inside each card
    // Confirmed: div with class "airlineName"
    private final By airlineNameLocator = By.xpath(
        ".//p[contains(@class,'airlineName')]"
    );

    // Price inside each card
    // Confirmed: span with classes "fontSize18 blackFont"
    private final By priceLocator = By.xpath(
        ".//span[contains(@class,'fontSize18')" +
        " and contains(@class,'blackFont')]"
    );

    
    

    // Number of stops inside card
    private final By stopsLocator = By.xpath(
        ".//div[contains(@class,'stop-info')]" +
        " | .//span[contains(@class,'stop')]"
    );

    // ─────────────────────────────────────────
    //  PUBLIC API
    // ─────────────────────────────────────────

    /**
     * Returns total number of flight cards
     * visible on results page.
     * MMT shows 3-4 cards on first load.
     */
    public int getFlightCardCount() {
    	
        try {
            List<WebElement> cards =
               getDriver().findElements(airlineNameLocator);
            logger.info("Flight cards found: {}",
                cards.size());
            return cards.size();
        } catch (Exception e) {
            logger.warn("Could not count flight cards: {}",
                e.getMessage());
            return 0;
        }
    }

    /**
     * Returns all visible flight cards.
     */
    public List<WebElement> getAllFlightCards() {
        return getDriver().findElements(allFlightCards);
    }

    /**
     * Gets airline name from card at given index.
     * Uses relative .// xpath inside card element.
     */
    public String getAirlineNameByIndex(int index) {
        List<WebElement> cards = getAllFlightCards();
        validateIndex(index, cards.size());
        WebElement card = cards.get(index);
        try {
            // .// means relative to THIS card element
            String name = card
                .findElement(airlineNameLocator)
                .getText().trim();
            logger.info("Card[{}] airline: {}",
                index, name);
            return name;
        } catch (Exception e) {
            logger.warn("Airline not found card[{}]: {}",
                index, e.getMessage());
            return "";
        }
    }

    /**
     * Gets raw price text from card.
     * Example return: "₹ 4,523"
     */
    public String getPriceByIndex(int index) {
        List<WebElement> cards = getAllFlightCards();
        validateIndex(index, cards.size());
        WebElement card = cards.get(index);
        try {
            String price = card
                .findElement(priceLocator)
                .getText().trim();
            logger.info("Card[{}] price: {}",
                index, price);
            return price;
        } catch (Exception e) {
            logger.warn("Price not found card[{}]: {}",
                index, e.getMessage());
            return "";
        }
    }

    /**
     * Extracts numeric price — strips ₹ and commas.
     * Example: "₹ 4,523" → 4523
     */
    public int getNumericPrice(int index) {
        String rawPrice = getPriceByIndex(index);
        try {
            String numeric =
                rawPrice.replaceAll("[^0-9]", "");
            int price = Integer.parseInt(numeric);
            logger.info("Card[{}] numeric price: {}",
                index, price);
            return price;
        } catch (Exception e) {
            logger.warn("Price parse failed '{}': {}",
                rawPrice, e.getMessage());
            return 0;
        }
    }

    /**
     * Gets stops info from card.
     * Example: "Non stop", "1 Stop"
     */
    public String getStopsByIndex(int index) {
        List<WebElement> cards = getAllFlightCards();
        validateIndex(index, cards.size());
        WebElement card = cards.get(index);
        try {
            String stops = card
                .findElement(stopsLocator)
                .getText().trim();
            logger.info("Card[{}] stops: {}",
                index, stops);
            return stops;
        } catch (Exception e) {
            logger.warn("Stops not found card[{}]: {}",
                index, e.getMessage());
            return "";
        }
    }

    /**
     * Validates all visible cards have
     * price greater than zero.
     */
    public boolean allCardsHaveValidPrices() {
        int count = getFlightCardCount();
        logger.info("Validating prices for {} cards",
            count);
        if (count == 0) {
            logger.error("No flight cards found");
            return false;
        }
        for (int i = 0; i < count; i++) {
            int price = getNumericPrice(i);
            if (price <= 0) {
                logger.error(
                    "Invalid price at card[{}]: {}",
                    i, getPriceByIndex(i));
                return false;
            }
        }
        logger.info("All {} cards have valid prices",
            count);
        return true;
    }

    /**
     * Validates all visible cards have
     * non-empty airline names.
     */
    public boolean allCardsHaveAirlineNames() {
        int count = getFlightCardCount();
        logger.info("Validating airlines for {} cards",
            count);
        if (count == 0) {
            logger.error("No flight cards found");
            return false;
        }
        for (int i = 0; i < count; i++) {
            String name = getAirlineNameByIndex(i);
            if (name.isEmpty()) {
                logger.error(
                    "Empty airline at card[{}]", i);
                return false;
            }
        }
        logger.info("All {} cards have airline names",
            count);
        return true;
    }

    // ─────────────────────────────────────────
    //  PRIVATE HELPERS
    // ─────────────────────────────────────────

    private void validateIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Card index " + index +
                " out of bounds. Total: " + size
            );
        }
    }
}