package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.components.TravellersComponent;
import io.github.nowshad.expedia.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class CommonStepDefs {

    private static final Logger logger =
        LogManager.getLogger(CommonStepDefs.class);

    private final HomePage homePage = new HomePage();

    // ─────────────────────────────────────────
    //  SHARED STEPS — homepage + search form
    //  Results steps moved to SearchResultsStepDefs
    // ─────────────────────────────────────────

    @Given("the user is on MakeMyTrip homepage")
    public void theUserIsOnHomepage() {
        logger.info("Verifying homepage loaded");
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

    @And("the user selects departure date as " +
         "{int} days from today")
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
}