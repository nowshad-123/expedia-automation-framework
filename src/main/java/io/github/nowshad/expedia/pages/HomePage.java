package io.github.nowshad.expedia.pages;

import io.github.nowshad.expedia.enums.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

	private static final Logger logger = LogManager.getLogger(HomePage.class);

	// ─────────────────────────────────────────
	// LOCATORS
	// ─────────────────────────────────────────

	private final By loginPopupClose = By.xpath("//span[@class='commonModal__close']");

	private final By flightsTab = By.xpath("//li[@class = 'menu_Flights']");

	private final By oneWayBtn = By.xpath("//li[@data-cy = 'oneWayTrip']");

	private final By roundTripBtn = By.xpath("//li[@data-cy='roundTrip']");

	private final By fromCityField = By.cssSelector("input#fromCity");

	private final By CityInputField = By.xpath("//input[contains(@class, 'react-autosuggest__input')]");

	private final By toCityField = By.cssSelector("input#toCity");

	private final By Suggestions = By.xpath("//ul[contains(@class,'react-autosuggest__suggestions-list')]//li");

	private final By searchBtn = By.cssSelector("a.primaryBtn");

	// ─────────────────────────────────────────
	// PAGE ACTIONS
	// ─────────────────────────────────────────

	/**
	 * Closes the login popup if it appears. MakeMyTrip shows this on first load.
	 * Uses isDisplayed() so it doesn't fail if popup doesn't appear.
	 */
	public HomePage closeLoginPopup() {
		if (isDisplayed(loginPopupClose)) {
			click(loginPopupClose);
			logger.info("Login popup closed");
		} else {
			logger.info("Login popup not present — skipping");
		}
		return this;
	}

	public HomePage clickFlightsTab() {
		click(flightsTab);
		logger.info("Clicked Flights tab");
		// Wait for React to re-render trip type buttons
		waitForPageLoad();
		waitForElement(oneWayBtn, WaitStrategy.VISIBLE);
		logger.info("Flight search form is ready");
		return this;
	}

	public HomePage selectOneWay() {
		// Use retry-safe method instead of plain click
		getElementWithRetry(oneWayBtn, WaitStrategy.CLICKABLE).click();
		logger.info("Selected One Way trip type");
		return this;
	}

	public HomePage selectRoundTrip() {
		getElementWithRetry(roundTripBtn, WaitStrategy.CLICKABLE).click();
		logger.info("Selected Round Trip type");
		return this;
	}

	/**
	 * Enters origin city and selects first suggestion from dropdown.
	 */
	public HomePage enterOrigin(String city) {
		click(fromCityField);
		typeWithoutClear(CityInputField, city);
		logger.info("Entered origin city: {}", city);
		waitForSuggestions();
		clickMatchedCity(Suggestions, city);
		logger.info("Selected origin suggestion for: {}", city);
		return this;
	}

	/**
	 * Enters destination city and selects first suggestion from dropdown.
	 */
	public HomePage enterDestination(String city) {
		click(toCityField);
		typeWithoutClear(CityInputField, city);
		logger.info("Entered destination city: {}", city);
		waitForSuggestions();
		clickMatchedCity(Suggestions, city);
		logger.info("Selected destination suggestion for: {}", city);
		return this;
	}

	/**
	 * Clicks the Search button.
	 */
	public HomePage clickSearch() {
		click(searchBtn);
		logger.info("Clicked Search button");
		return this;
	}

	// ─────────────────────────────────────────
	// VERIFICATION METHODS
	// ─────────────────────────────────────────

	public boolean isFlightsTabVisible() {
		return isDisplayed(flightsTab);
	}

	public boolean isHomePageLoaded() {
		return isDisplayed(oneWayBtn);
	}

	// ─────────────────────────────────────────
	// PRIVATE HELPERS
	// ─────────────────────────────────────────

	private void waitForSuggestions() {
		// Wait for suggestion dropdown to appear
		waitForElement(Suggestions, WaitStrategy.VISIBLE);
		logger.debug("Suggestion dropdown appeared");
	}
}