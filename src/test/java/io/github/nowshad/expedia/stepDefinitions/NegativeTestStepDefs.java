package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.models.FlightTestData;
import io.github.nowshad.expedia.pages.NegativeTestPage;
import io.github.nowshad.expedia.utilis.JsonDataReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class NegativeTestStepDefs {

	private static final Logger logger = LogManager.getLogger(NegativeTestStepDefs.class);

	private final NegativeTestPage negativePage = new NegativeTestPage();

	// ─────────────────────────────────────────
	// SAME AIRPORT ASSERTIONS
	// ─────────────────────────────────────────

	@Then("the same airport error should be displayed")
	public void sameAirportErrorShouldBeDisplayed() {
		Assert.assertTrue(negativePage.isSameAirportErrorDisplayed(), "Same airport error message not displayed");
		logger.info("Same airport error verified ✅");
	}

	@And("the error message should contain {string}")
	public void errorMessageShouldContain(String expectedText) {
		String actual = negativePage.getSameAirportErrorText();
		logger.info("Expected: {} | Actual: {}", expectedText, actual);
		Assert.assertTrue(actual.contains(expectedText),
				"Error text mismatch. " + "Expected to contain: " + expectedText + " | Actual: " + actual);
	}


	@And("the error text should match expected " + "from test data")
	public void errorTextShouldMatchTestData() {
		// Get current test data from
		// DataDrivenStepDefs context
		// Use JsonDataReader directly for NEG_001
		FlightTestData data = JsonDataReader.getByTestId("NEG_001");
		String expected = data.getExpectedError();
		String actual = negativePage.getSameAirportErrorText();
		logger.info("DDT Error check — " + "Expected: {} | Actual: {}", expected, actual);
		Assert.assertTrue(actual.contains(expected), "Error mismatch. Expected: " + expected + " | Actual: " + actual);
	}

	// ─────────────────────────────────────────
	// NO RESULTS ASSERTIONS
	// ─────────────────────────────────────────

	@Then("the no flights found page should " + "be displayed")
	public void noFlightsPageShouldBeDisplayed() {
		negativePage.waitForNoResultsPage();
		Assert.assertTrue(negativePage.isNoFlightsPageDisplayed(), "No flights page not displayed");
		logger.info("No flights page verified ✅");
	}

	@And("the no flights title should contain {string}")
	public void noFlightsTitleShouldContain(String expectedTitle) {
		String actual = negativePage.getNoFlightsTitleText();
		logger.info("No flights title: {}", actual);
		Assert.assertTrue(actual.contains(expectedTitle),
				"Title mismatch. Expected: " + expectedTitle + " | Actual: " + actual);
	}

	@And("the go back button should be visible")
	public void goBackButtonShouldBeVisible() {
		Assert.assertTrue(negativePage.isGoBackButtonVisible(), "Go Back button not visible");
		logger.info("Go Back button visible ✅");
	}

	@And("the no flights title should match " + "expected from test data")
	public void noFlightsTitleShouldMatchTestData() {
		FlightTestData data = JsonDataReader.getByTestId("NEG_002");
		String expected = data.getExpectedErrMsg();
		String actual = negativePage.getNoFlightsTitleText();
		logger.info("DDT No results check — " + "Expected: {} | Actual: {}", expected, actual);
		Assert.assertTrue(actual.contains(expected), "Title mismatch. Expected: " + expected + " | Actual: " + actual);
	}
}