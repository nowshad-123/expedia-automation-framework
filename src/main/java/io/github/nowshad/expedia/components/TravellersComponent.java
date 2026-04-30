package io.github.nowshad.expedia.components;

import io.github.nowshad.expedia.enums.WaitStrategy;
import io.github.nowshad.expedia.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class TravellersComponent extends BasePage {

	private static final Logger logger = LogManager.getLogger(TravellersComponent.class);

	// ─────────────────────────────────────────
	// TRAVEL CLASS ENUM
	// ─────────────────────────────────────────

	public enum TravelClass {
		ECONOMY("Economy"), PREMIUM_ECONOMY("Premium Economy"), BUSINESS("Business"), FIRST("First");

		private final String displayText;

		TravelClass(String displayText) {
			this.displayText = displayText;
		}

		public String getDisplayText() {
			return displayText;
		}
	}

	// ─────────────────────────────────────────
	// LOCATORS — confirmed from your DevTools
	// ─────────────────────────────────────────

	// Travellers input field — opens the panel
	private final By travellersInput = By.xpath("//div[@data-cy = 'flightTraveller']");

	// Adults selector boxes
	// Returns 10 li elements: 1,2,3...9,>9
	private final By adultBoxes = By
			.xpath("//ul[contains(@class,'guestCounter')]" + "//li[contains(@data-cy,'adults')]");

	// Children selector boxes
	// Returns 8 li elements: 0,1,2...6,>6
	private final By childBoxes = By
			.xpath("//ul[contains(@class,'guestCounter')]" + "//li[contains(@data-cy,'children')]");

	// Infants selector boxes
	// Returns 8 li elements: 0,1,2...6,>6
	private final By infantBoxes = By
			.xpath("//ul[contains(@class,'guestCounter')]" + "//li[contains(@data-cy,'infants')]");

// Apply button — confirms and closes panel
	private final By applyButton = By.xpath("//button[normalize-space()='APPLY']");

	// ─────────────────────────────────────────
	// PUBLIC API
	// ─────────────────────────────────────────

	/**
	 * Opens travellers panel, sets all counts, selects travel class, and clicks
	 * APPLY.
	 *
	 * @param adults      1–9 (min 1)
	 * @param children    0–6
	 * @param infants     0–6 (cannot exceed adults)
	 * @param travelClass Economy/Premium/Business/First
	 */
	public void setTravellers(int adults, int children, int infants, TravelClass travelClass) {

		logger.info("Setting travellers — Adults:{} " + "Children:{} Infants:{} Class:{}", adults, children, infants,
				travelClass.getDisplayText());

		// Validate infants <= adults
		if (infants > adults) {
			throw new IllegalArgumentException("Infants (" + infants + ") cannot exceed " + "adults (" + adults + ")");
		}

		// Open panel
		openTravellersPanel();

		// Set each category
		selectAdults(adults);
		selectChildren(children);
		selectInfants(infants);
		selectTravelClass(travelClass);

		// Confirm
		clickApply();

		logger.info("Travellers applied successfully");
	}

	/**
	 * Convenience method — 1 adult, Economy class. Use for basic search scenarios.
	 */
	public void setDefaultTravellers() {
		setTravellers(1, 0, 0, TravelClass.ECONOMY);
	}

	// ─────────────────────────────────────────
	// PRIVATE HELPERS
	// ─────────────────────────────────────────

	private void openTravellersPanel() {
		click(travellersInput);
		logger.info("Travellers panel opened");
		// Wait for adult boxes to appear
		waitForElement(adultBoxes, WaitStrategy.VISIBLE);
		logger.info("Travellers panel visible");
	}

	/**
	 * Selects adult count by clicking the corresponding li box. Adults list: index
	 * 0=1adult, 1=2adults... (list starts from 1, not 0)
	 */
	private void selectAdults(int count) {
		logger.info("Selecting {} adult(s)", count);
		// Adults boxes show 1,2,3...9,>9
		// Index = count - 1 (since list starts at 1)
		if (count < 1 || count > 9) {
			throw new IllegalArgumentException("Adults must be between 1 and 9. Got: " + count);
		}
		// Position = count - 1 (0-indexed)
		// Adult 1 = position[0], Adult 2 = position[1]
		clickBoxByPosition(adultBoxes, count - 1, "adult", count);
	}

	/**
	 * Selects children count. Children list: index 0=0children, 1=1child...
	 */
	private void selectChildren(int count) {
		logger.info("Selecting {} child(ren)", count);
		if (count < 0 || count > 6) {
			throw new IllegalArgumentException("Children must be between 0 and 6. Got: " + count);
		}
		// Children boxes show 0,1,2...6,>6
		// Index = count (since list starts at 0)
		clickBoxByPosition(childBoxes, count, "children", count);
	}

	/**
	 * Selects infants count. Infants list: index 0=0infants, 1=1infant...
	 */
	private void selectInfants(int count) {
		logger.info("Selecting {} infant(s)", count);
		if (count < 0 || count > 6) {
			throw new IllegalArgumentException("Infants must be between 0 and 6. Got: " + count);
		}
		clickBoxByPosition(infantBoxes, count, "infants", count);
	}

	/**
	 * Selects travel class by matching display text.
	 */
	private void selectTravelClass(TravelClass travelClass) {
		logger.info("Selecting travel class: {}", travelClass.getDisplayText());

		// Find the li that contains the class text
		By classLocator = By.xpath("//ul[contains(@class,'guestCounter')]" + "//li[contains(@data-cy,'travelClass')"
				+ " and contains(.,'" + travelClass.getDisplayText() + "')]");

		try {
			click(classLocator);
			logger.info("Travel class selected: {}", travelClass.getDisplayText());
		} catch (Exception e) {
			logger.warn("Could not select travel class: {}. " + "Using default.", travelClass.getDisplayText());
		}
	}

	/**
	 * Clicks a box at a specific index position from a list of li elements.
	 *
	 * @param listLocator locator returning all li boxes
	 * @param index       0-based position to click
	 * @param label       for logging only
	 * @param value       expected value at that position
	 */
	private void clickBoxByPosition(By listLocator, int index, String label, int value) {

		// Build XPath to nth element using position()
		// position() is 1-indexed in XPath
		By nthBox = By.xpath("(" + xpathFrom(listLocator) + ")[" + (index + 1) + "]");

		try {
			waitForElement(nthBox, WaitStrategy.CLICKABLE);
			click(nthBox);
			logger.info("Selected {} = {}", label, value);
		} catch (Exception e) {
			logger.error("Failed to select {} = {} " + "at index {}: {}", label, value, index, e.getMessage());
			throw new RuntimeException("Could not select " + label + "=" + value + " at position " + index, e);
		}
	}

	/**
	 * Extracts XPath string from a By.xpath locator. Used to build nth-element
	 * selectors.
	 */
	private String xpathFrom(By locator) {
		// By.xpath toString = "By.xpath: //..."
		// Strip the "By.xpath: " prefix
		return locator.toString().replace("By.xpath: ", "");
	}

	private void clickApply() {
		waitForElement(applyButton, WaitStrategy.CLICKABLE);
		click(applyButton);
		logger.info("Clicked APPLY button");
	}
}
