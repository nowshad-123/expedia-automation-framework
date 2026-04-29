package io.github.nowshad.expedia.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.nowshad.expedia.components.TravellersComponent;
import io.github.nowshad.expedia.pages.HomePage;
import io.github.nowshad.expedia.pages.SearchResultsPage;

public class FlightSearchStepDefs {

	private static final Logger logger = LogManager.getLogger(FlightSearchStepDefs.class);

	// Page object instantiated here —
	// driver is ready because @Before already ran
	private final HomePage homePage = new HomePage();

	private final SearchResultsPage resultsPage = new SearchResultsPage();

	@Given("the user is on MakeMyTrip homepage")
	public void theUserIsOnMakeMyTripHomepage() {
		logger.info("User is on MakeMyTrip homepage");
		Assert.assertTrue(homePage.isHomePageLoaded(), "Homepage did not load correctly");
	}

	@When("the user closes the login popup if present")
	public void theUserClosesTheLoginPopup() {
		homePage.closeLoginPopup();
	}

	@And("the user clicks on Flights tab")
	public void theUserClicksOnFlightsTab() {
		homePage.clickFlightsTab();
	}

	@And("the user selects One Way trip")
	public void theUserSelectsOneWayTrip() {
		homePage.selectOneWay();
	}

	@And("the user enters origin city as {string}")
	public void theUserEntersOriginCity(String city) {
		homePage.enterOrigin(city);
	}

	@And("the user enters destination city as {string}")
	public void theUserEntersDestinationCity(String city) {
		homePage.enterDestination(city);
	}

	@And("the user selects departure date as {int} days from today")
	public void theUserSelectsDepartureDateAsDaysFromToday(int daysFromToday) {
		logger.info("Selecting departure date: {} days from today", daysFromToday);
		homePage.selectDepartureDate(daysFromToday);
	}

	@And("the user selects {int} adults {int} children " + "{int} infants in Economy class")
	public void theUserSelectsTravellers(int adults, int children, int infants) {
		logger.info("Selecting travellers: {} adults " + "{} children {} infants Economy", adults, children, infants);
		homePage.selectTravellers(adults, children, infants, TravellersComponent.TravelClass.ECONOMY);
	}

	@And("the user clicks on Search button")
	public void theUserClicksOnSearchButton() {
		homePage.clickSearch();
	}

	@Then("the search results page should be displayed")
	public void theSearchResultsPageShouldBeDisplayed() {
		logger.info("Verifying search results page");
		resultsPage.waitForResultsToLoad();
		Assert.assertTrue(resultsPage.isResultsPageLoaded(),
				"Expected flight search results URL | Actual: " + resultsPage.getSearchUrl());
	}

	@And("the search URL should contain {string} and {string}")
	public void theSearchUrlShouldContain(String origin, String dest) {
		logger.info("Verifying route in URL: {}-{}", origin, dest);
		Assert.assertTrue(resultsPage.isCorrectRoute(origin, dest),
				"Expected URL to contain: " + origin + " and " + dest + " | Actual URL: " + resultsPage.getSearchUrl());
	}
}
