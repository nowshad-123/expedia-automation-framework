package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class FlightSearchStepDefs {

    private static final Logger logger =
        LogManager.getLogger(FlightSearchStepDefs.class);

    // Page object instantiated here —
    // driver is ready because @Before already ran
    private final HomePage homePage = new HomePage();

    @Given("the user is on MakeMyTrip homepage")
    public void theUserIsOnMakeMyTripHomepage() {
        logger.info("User is on MakeMyTrip homepage");
        Assert.assertTrue(
            homePage.isHomePageLoaded(),
            "Homepage did not load correctly"
        );
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
    public void theUserSelectsDepartureDateAsDaysFromToday(
            int daysFromToday) {
        logger.info("Selecting departure date: {} days from today",
            daysFromToday);
        homePage.selectDepartureDate(daysFromToday);
    }

    @And("the user clicks on Search button")
    public void theUserClicksOnSearchButton() {
        homePage.clickSearch();
    }

    @Then("the search results page should be displayed")
    public void theSearchResultsPageShouldBeDisplayed() {
        logger.info("Verifying search results page loaded");
        // Basic URL check — results page has
        // /flight/search in the URL
        String currentUrl = homePage.getCurrentUrl();
        logger.info("Current URL after search: {}", currentUrl);
        Assert.assertTrue(
            currentUrl.contains("flight") ||
            currentUrl.contains("search"),
            "Expected flight search results URL | Actual: "
            + currentUrl
        );
    }
}
