package io.github.nowshad.expedia.components;

import io.github.nowshad.expedia.enums.WaitStrategy;
import io.github.nowshad.expedia.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class FilterComponent extends BasePage {

	private static final Logger logger = LogManager.getLogger(FilterComponent.class);

	// ─────────────────────────────────────────
	// LOCATORS — confirmed from DevTools
	// ─────────────────────────────────────────

	// Airlines section header
	private final By airlinesSection = By.xpath("//*[normalize-space()='Airlines']");

	// All airline checkboxes
	private final By airlineCheckboxes = By
			.xpath("//p[normalize-space()='Airlines']" + "/following-sibling::div//input[@type='checkbox']");

	// Specific airline by name
	// Usage: airlineByName("IndiGo")
	private By airlineByName(String name) {
		return By.xpath("//*[contains(text(),'"+name+"')]/ancestor::label");
	}

	private final By stopSection = By.xpath("//*[normalize-space()='Stops']");

	// Non Stop checkbox — confirmed locator
	private final By nonStopCheckbox = By.xpath("(//p[@class='checkboxTitle'][normalize-space()='Non Stop'])[2]");

	private final By nonStopCheckboxFallback = By.xpath("//div[contains(text(), 'Non Stop')]");

	// One Stop checkbox — confirmed locator
	private final By oneStopCheckbox = By.xpath("(//p[@class='checkboxTitle'][normalize-space()='1 Stop'])[2]");

	private final By oneStopCheckboxFallback = By.xpath("//*[contains(text(), '1 Stop')]");

	// Price slider container
	private final By priceSlider = By
			.xpath("//div[contains(@class,'make-flex') " + "and contains(@class,'sort-price')]");

	// Price slider handles — two handles
	private final By sliderHandles = By
			.xpath("//div[contains(@class,'make-flex') " + "and contains(@class,'sort-price')]"
					+ "//div[@role='slider']" + " | //div[contains(@class,'rc-slider-handle')]");

	// Clear all filters button — confirmed locator
	private final By clearAllButton = By.xpath("//span[@class='clearFilter']");

	// Results container — to count after filter
	private final By flightCards = By.xpath("//div[contains(@class,'appendBottom5')]");

	// ─────────────────────────────────────────
	// AIRLINE FILTER
	// ─────────────────────────────────────────

	/**
	 * Selects a specific airline by name. Results update automatically — confirmed.
	 *
	 * @param airlineName e.g. "IndiGo", "Air India"
	 */
	public FilterComponent selectAirline(String airlineName) {
		logger.info("Selecting airline: {}", airlineName);

		By locator = airlineByName(airlineName);

		try {
			// Scroll to airlines section first
			scrollIntoView(airlinesSection);

			WebElement checkbox = waitForElement(locator, WaitStrategy.PRESENCE);

			// Use JS click — checkboxes in filter
			// panels often have overlay issues
			if (!checkbox.isSelected()) {
				jsClick(locator);
				logger.info("Airline selected: {}", airlineName);
			} else {
				logger.info("Airline already selected: {}", airlineName);
			}

			// Wait for results to update
			waitForResultsToUpdate();

		} catch (Exception e) {
			logger.error("Failed to select airline {}: {}", airlineName, e.getMessage());
			throw new RuntimeException("Could not select airline: " + airlineName, e);
		}
		return this;
	}

	/**
	 * Returns list of all available airline names in the filter panel.
	 */
	public List<WebElement> getAvailableAirlines() {
		scrollIntoView(airlinesSection);
		return waitForElements(airlineCheckboxes);
	}

	// ─────────────────────────────────────────
	// STOPS FILTER
	// ─────────────────────────────────────────

	/**
	 * Selects Non Stop filter. Confirmed locator: input[id*='nonStop']
	 */
	public FilterComponent selectNonStop() {
		scrollIntoView(stopSection);
		try {
			logger.info("Selecting Non Stop filter");
			applyStopsFilter(nonStopCheckbox, "Non Stop");
		} catch (Exception e) {
			logger.info("{} Element not found - trying with fallback", nonStopCheckbox);
			applyStopsFilter(nonStopCheckboxFallback, "Non Stop");
		}
		return this;
	}

	/**
	 * Selects One Stop filter. Confirmed locator: input[id*='oneStop']
	 */
	public FilterComponent selectOneStop() {
		scrollIntoView(stopSection);
		try {
			logger.info("Selecting One Stop filter");
			applyStopsFilter(oneStopCheckbox, "one Stop");
		} catch (Exception e) {
			logger.info("{} Element not found - trying with fallback {}", oneStopCheckbox, oneStopCheckboxFallback);
			applyStopsFilter(oneStopCheckboxFallback, "One Stop");
		}
		return this;
	}

	private void applyStopsFilter(By checkboxLocator, String label) {
		try {

			WebElement checkbox = waitForElement(checkboxLocator, WaitStrategy.PRESENCE);
	

			if (!checkbox.isSelected()) {
				click(checkboxLocator);
				logger.info("{} filter applied", label);
			} else {
				logger.info("{} already selected", label);
			}

			waitForResultsToUpdate();

		} catch (Exception e) {
			logger.error("Failed to apply {} filter: {}", label, e.getMessage());
			throw new RuntimeException("Could not apply filter: " + label, e);
		}
	}

	// ─────────────────────────────────────────
	// PRICE FILTER
	// ─────────────────────────────────────────

	/**
	 * Moves price slider to set maximum price. Uses Actions to drag the right
	 * handle left.
	 *
	 * @param percentage 0-100, how far to move left handle right e.g. 50 = midpoint
	 */
	public FilterComponent setMaxPrice(int percentage) {
		logger.info("Setting max price to {}% of range", percentage);
		try {
			scrollIntoView(priceSlider);

			List<WebElement> handles = waitForElements(sliderHandles);

			if (handles.size() < 2) {
				logger.warn("Price slider handles not " + "found. Skipping price filter.");
				return this;
			}

			// Right handle = max price
			WebElement maxHandle = handles.get(1);
			int sliderWidth = maxHandle.getSize().getWidth();

			// Calculate offset — negative moves left
			// reducing the max price
			int offset = -(sliderWidth * (100 - percentage) / 100);

			new Actions(getDriver()).clickAndHold(maxHandle).moveByOffset(offset, 0).release().perform();

			logger.info("Max price slider moved: {}px", offset);
			waitForResultsToUpdate();

		} catch (Exception e) {
			logger.warn("Price slider interaction " + "failed: {}", e.getMessage());
		}
		return this;
	}

	// ─────────────────────────────────────────
	// CLEAR FILTERS
	// ─────────────────────────────────────────

	/**
	 * Clears all applied filters. Confirmed locator: span.clearFilter
	 */
	public FilterComponent clearAllFilters() {
		logger.info("Clearing all filters");
		try {
			if (isDisplayed(clearAllButton)) {
				click(clearAllButton);
				logger.info("All filters cleared");
				waitForResultsToUpdate();
			} else {
				logger.info("No filters to clear");
			}
		} catch (Exception e) {
			logger.warn("Clear filters failed: {}", e.getMessage());
		}
		return this;
	}

	// ─────────────────────────────────────────
	// RESULTS COUNT
	// ─────────────────────────────────────────

	/**
	 * Returns current visible flight card count. Use before and after filter to
	 * verify change.
	 */
	public int getCurrentResultCount() {
		try {
			List<WebElement> cards = waitForElements(flightCards);
			logger.info("Current result count: {}", cards.size());
			return cards.size();
		} catch (Exception e) {
			logger.warn("Could not count results: {}", e.getMessage());
			return 0;
		}
	}

	/**
	 * Verifies filter is applied by checking result count changed after filter.
	 *
	 * @param beforeCount count before filter
	 * @return true if count changed
	 */
	public boolean didResultsChange(int beforeCount) {
		int afterCount = getCurrentResultCount();
		boolean changed = afterCount != beforeCount;
		logger.info("Results before: {} | after: {} | " + "changed: {}", beforeCount, afterCount, changed);
		return changed;
	}

	// ─────────────────────────────────────────
	// PRIVATE HELPERS
	// ─────────────────────────────────────────

	/**
	 * Waits for results to update after filter. No spinner confirmed — just brief
	 * pause for React re-render.
	 */
	private void waitForResultsToUpdate() {
		try {
			Thread.sleep(500);
			waitForPageLoad();
			logger.info("Results updated after filter");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}