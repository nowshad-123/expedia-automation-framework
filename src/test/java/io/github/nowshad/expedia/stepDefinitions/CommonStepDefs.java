package io.github.nowshad.expedia.stepDefinitions;


import io.github.nowshad.expedia.components.TravellersComponent;
import io.github.nowshad.expedia.pages.HomePage;
import io.github.nowshad.expedia.pages.SearchResultsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class CommonStepDefs {

    private static final Logger logger =
        LogManager.getLogger(CommonStepDefs.class);

    private final HomePage homePage = new HomePage();
    private final SearchResultsPage resultsPage =
        new SearchResultsPage();

    // ─────────────────────────────────────────
    //  SHARED STEPS — used by both
    //  FlightSearch and RoundTrip scenarios
    // ─────────────────────────────────────────

    @Given("the user is on MakeMyTrip homepage")
    public void theUserIsOnHomepage() {
        logger.info("Verifying MakeMyTrip homepage loaded");
        Assert.assertTrue(
            homePage.isHomePageLoaded(),
            "Homepage did not load correctly"
        );
    }

    @When("the user closes the login popup if present")
    public void closeLoginPopup() {
        homePage.closeLoginPopup();
    }

    @And("the user clicks on Flights tab")
    public void clickFlightsTab() {
        homePage.clickFlightsTab();
    }

    @And("the user enters origin city as {string}")
    public void enterOriginCity(String city) {
        homePage.enterOrigin(city);
    }

    @And("the user enters destination city as {string}")
    public void enterDestinationCity(String city) {
        homePage.enterDestination(city);
    }

    @And("the user selects departure date as {int} days from today")
    public void selectDepartureDate(int days) {
        homePage.selectDepartureDate(days);
    }

    @And("the user selects {int} adults {int} children " +
         "{int} infants in Economy class")
    public void selectTravellers(
            int adults, int children, int infants) {
        homePage.selectTravellers(
            adults, children, infants,
            TravellersComponent.TravelClass.ECONOMY
        );
    }

    @And("the user clicks on Search button")
    public void clickSearch() {
        homePage.clickSearch();
    }

    @Then("the search results page should be displayed")
    public void verifyResultsPage() {
        resultsPage.waitForResultsToLoad();
        Assert.assertTrue(
            resultsPage.isResultsPageLoaded(),
            "Results page not loaded. URL: " +
            resultsPage.getSearchUrl()
        );
    }

    @And("the search URL should contain {string} and {string}")
    public void verifyRouteInUrl(
            String origin, String dest) {
        Assert.assertTrue(
            resultsPage.isCorrectRoute(origin, dest),
            "Route not found. Expected: " +
            origin + "-" + dest +
            " | URL: " + resultsPage.getSearchUrl()
        );
    }
}
