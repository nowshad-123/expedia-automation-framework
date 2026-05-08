package io.github.nowshad.expedia.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.github.nowshad.expedia.components.TravellersComponent;
import io.github.nowshad.expedia.models.FlightTestData;
import io.github.nowshad.expedia.pages.HomePage;
import io.github.nowshad.expedia.pages.SearchResultsPage;
import io.github.nowshad.expedia.utilis.ExcelDataReader;
import io.github.nowshad.expedia.utilis.JsonDataReader;

public class DataDrivenStepDefs {

    private static final Logger logger =
        LogManager.getLogger(DataDrivenStepDefs.class);

    private final HomePage homePage = new HomePage();

    // Holds current test data for this scenario
    private FlightTestData currentTestData;
    
 // Add field
    private final SearchResultsPage resultsPage =
        new SearchResultsPage();

    // ─────────────────────────────────────────
    //  DATA LOADING STEPS
    // ─────────────────────────────────────────

    @Given("the flight data is loaded from JSON " +
           "for testId {string}")
    public void loadFlightDataFromJson(String testId) {
    	currentTestData =
    	        JsonDataReader.getByTestId(testId);
        logger.info("JSON data loaded for: {} | {}→{}",
            currentTestData.getTestId(),
            currentTestData.getOrigin(),
            currentTestData.getDestination());
    }

    @Given("the flight data is loaded from Excel " +
           "for testId {string}")
    public void loadFlightDataFromExcel(String testId) {
        currentTestData =
            ExcelDataReader.getFlightById(testId);
        logger.info("Excel data loaded for: {} | {}→{}",
            currentTestData.getTestId(),
            currentTestData.getOrigin(),
            currentTestData.getDestination());
    }

    // ─────────────────────────────────────────
    //  DATA DRIVEN INTERACTION STEPS
    // ─────────────────────────────────────────

    @And("the user enters origin from test data")
    public void enterOriginFromTestData() {
        validateDataLoaded();
        logger.info("Entering origin from data: {}",
            currentTestData.getOrigin());
        homePage.enterOrigin(
            currentTestData.getOrigin());
    }

    @And("the user enters destination from test data")
    public void enterDestinationFromTestData() {
        validateDataLoaded();
        logger.info("Entering destination from data: {}",
            currentTestData.getDestination());
        homePage.enterDestination(
            currentTestData.getDestination());
    }

    @And("the user selects departure date from test data")
    public void selectDepartureDateFromTestData() {
        validateDataLoaded();
        logger.info("Selecting departure: {} days",
            currentTestData.getDepartureDays());
        homePage.selectDepartureDate(
            currentTestData.getDepartureDays());
    }

    @And("the user selects travellers from test data")
    public void selectTravellersFromTestData() {
        validateDataLoaded();
        logger.info("Selecting travellers from data: " +
            "{} adults {} children {} infants {}",
            currentTestData.getAdults(),
            currentTestData.getChildren(),
            currentTestData.getInfants(),
            currentTestData.getTravelClass());

        TravellersComponent.TravelClass travelClass =
            TravellersComponent.TravelClass.valueOf(
                currentTestData.getTravelClass()
                    .toUpperCase()
                    .replace(" ", "_")
            );

        homePage.selectTravellers(
            currentTestData.getAdults(),
            currentTestData.getChildren(),
            currentTestData.getInfants(),
            travelClass
        );
    }
 

    
    @And("the user applies filter from test data")
    public void applyFilterFromTestData() {
        validateDataLoaded();
        String filterType =
            currentTestData.getFilterType();
        logger.info("Applying filter type: {}",
            filterType);

        switch (filterType.toLowerCase()) {
            case "nonstop":
                resultsPage.filterByNonStop();
                break;
            case "onestop":
                resultsPage.filterByOneStop();
                break;
            case "airline":
                String airline =
                    currentTestData.getAirlineName();
                resultsPage.filterByAirline(airline);
                break;
            case "combined":
                resultsPage.filterByNonStop();
                String combinedAirline =
                    currentTestData.getAirlineName();
                if (!combinedAirline.isEmpty()) {
                    resultsPage.filterByAirline(
                        combinedAirline);
                }
                break;
            default:
                logger.warn("Unknown filter type: {}",
                    filterType);
        }
    }

    // ─────────────────────────────────────────
    //  PRIVATE HELPERS
    // ─────────────────────────────────────────

    private void validateDataLoaded() {
        if (currentTestData == null) {
            throw new RuntimeException(
                "Test data not loaded. Call " +
                "'the flight data is loaded from' " +
                "step first."
            );
        }
    }
}