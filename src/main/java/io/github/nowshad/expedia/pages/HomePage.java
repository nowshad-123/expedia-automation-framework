package io.github.nowshad.expedia.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import io.github.nowshad.expedia.components.DatePickerComponent;
import io.github.nowshad.expedia.components.SortComponent;
import io.github.nowshad.expedia.components.TravellersComponent;
import io.github.nowshad.expedia.enums.WaitStrategy;

public class HomePage extends BasePage {

	private static final Logger logger = LogManager.getLogger(HomePage.class);

	// ─────────────────────────────────────────
	// LOCATORS
	// ─────────────────────────────────────────

	private final By loginPopupClose = By.xpath("//span[@class='commonModal__close']");

	private final By flightsTab = By.xpath("//li[@class = 'menu_Flights']");
	
	private final By flightsTabStatus = By.xpath(".//a[contains(@class,'active')]");

	private final By oneWayBtn = By.xpath("//li[@data-cy = 'oneWayTrip']");

	private final By roundTripBtn = By.xpath("//li[@data-cy='roundTrip']");

	private final By fromCityField = By.cssSelector("input#fromCity");

	private final By CityInputField = By.xpath("//input[contains(@class, 'react-autosuggest__input')]");

	private final By toCityField = By.cssSelector("input#toCity");

	private final By Suggestions = By.xpath("//ul[contains(@class,'react-autosuggest__suggestions-list')]//li");

	private final By searchBtn = By.cssSelector("a.primaryBtn");
	
	private final By commonOverlayContainerLocator  = By.xpath("//div[contains(@class,'commonOverlay')]");
	
	
	private void dismissSpaOverlay() {
	    By spaOverlay = By.xpath(
	        "//div[contains(@class,'spa-classic-peek')]"
	    );
	    By spaClose = By.xpath(
	        "//div[contains(@class,'spa-classic-peek')]" +
	        "//span[contains(@class,'close')]" +
	        " | //div[contains(@class,'spa-classic-peek')]" +
	        "//button"
	    );

	    try {
	        if (isDisplayed(spaOverlay)) {
	            logger.info("spa-classic-peek overlay " +
	                "detected — dismissing");
	            try {
	                // Try clicking close button first
	                jsClick(spaClose);
	                logger.info("SPA overlay closed " +
	                    "via close button");
	            } catch (Exception e) {
	                // If no close button — JS hide it
	                ((org.openqa.selenium.JavascriptExecutor)
	                    getDriver())
	                    .executeScript(
	                        "document.querySelector(" +
	                        "'.spa-classic-peek')" +
	                        ".style.display='none';"
	                    );
	                logger.info("SPA overlay hidden via JS");
	            }
	        }
	    } catch (Exception e) {
	        logger.debug("No spa overlay present: {}",
	            e.getMessage());
	    }
	}
	

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
	    if (!isDisplayed(flightsTabStatus)) {
	        click(flightsTab);
	        logger.info("Clicked Flights tab");
	    } else {
	        logger.info("Flights tab already active");
	    }

	    // Common post-condition
	    waitForPageLoad();
	    waitForElement(oneWayBtn, WaitStrategy.VISIBLE);
	    logger.info("Flight search form is ready");

	    return this;
	}

	public HomePage selectOneWay() {
		
	    getElementWithRetry(oneWayBtn, WaitStrategy.CLICKABLE).click();
		logger.info("Selected One Way trip type");
		return this;
	}

	public HomePage selectRoundTrip() {
		// Dismiss spa-classic-peek overlay if present
	    dismissSpaOverlay();
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

		// After destination selected, MMT auto-opens calendar
		// Give React a moment to render it
		waitForPageLoad();

		return this;

	}

	/**
	 * Clicks the Search button.
	 */
	public HomePage clickSearch() {
		logger.info("Preparing to click search...");

	    // Hover over search button before clicking
	    // Simulates human mouse movement
	    hover(searchBtn);

	    // Brief pause — human reaction time
	    try {
	        Thread.sleep(1500);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	    click(searchBtn);
	    logger.info("Clicked Search button");
	    return this;
	}

	// Add to actions section
	/**
	 * Opens the departure date calendar and selects a date N days from today.
	 */
	public HomePage selectDepartureDate(int daysFromToday) {
		logger.info("Selecting departure date: {} days from today", daysFromToday);

		// Calendar auto-opens after destination city selection
		// confirmed from manual exploration — no click needed
		DatePickerComponent datePicker = new DatePickerComponent();
		datePicker.selectDepartureDate(daysFromToday);

		logger.info("Departure date selection complete");
		return this;
	}

	public HomePage selectReturnDate(int departureDays, int returnDays) {

		// Business rule guard — MMT silently resets
		// departure if return < departure
		if (returnDays <= departureDays) {
			throw new IllegalArgumentException("Return date (" + returnDays + " days) " + "must be after departure ("
					+ departureDays + " days from today)");
		}

		logger.info("Selecting return date: {} days from today", returnDays);

		// Calendar is already open — stays open after
		// departure selection (confirmed Day 7 exploration)
		DatePickerComponent datePicker = new DatePickerComponent();
		datePicker.selectReturnDate(returnDays);

		logger.info("Return date selected successfully");
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

	/**
	 * Selects travellers with travel class. TravellersComponent handles panel open
	 * internally.
	 */
	public HomePage selectTravellers(int adults, int children, int infants,
			TravellersComponent.TravelClass travelClass) {

		logger.info("Configuring travellers");
		TravellersComponent travellers = new TravellersComponent();
		travellers.setTravellers(adults, children, infants, travelClass);
		return this;
	}

	/**
	 * Default: 1 adult, Economy class.
	 */
	public HomePage selectDefaultTravellers() {
		TravellersComponent travellers = new TravellersComponent();
		travellers.setDefaultTravellers();
		return this;
	}
	
	public HomePage closeOverlayIfPresent() {
	    if (isDisplayed(commonOverlayContainerLocator)) {
	        logger.info("Overlay is present, attempting to close");
	        SortComponent sort = new SortComponent();
	        sort.closeOverlay();
	    } else {
	        logger.info("Overlay not present, skipping");
	    }
	    return this;
	}
}

