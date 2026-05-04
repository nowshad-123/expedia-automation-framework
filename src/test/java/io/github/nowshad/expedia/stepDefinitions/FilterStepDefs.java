package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.SearchResultsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class FilterStepDefs {

    private static final Logger logger =
        LogManager.getLogger(FilterStepDefs.class);

    private final SearchResultsPage resultsPage =
        new SearchResultsPage();

    // Stores count before filter for comparison
    private int resultCountBeforeFilter = 0;

    @Given("the user notes the current result count")
    public void noteCurrentResultCount() {
        resultCountBeforeFilter =
            resultsPage.getCurrentResultCount();
        logger.info("Result count before filter: {}",
            resultCountBeforeFilter);
    }

    @When("the user applies Non Stop filter")
    public void applyNonStopFilter() {
        resultsPage.filterByNonStop();
        logger.info("Non Stop filter applied");
    }

    @When("the user applies One Stop filter")
    public void applyOneStopFilter() {
        resultsPage.filterByOneStop();
        logger.info("One Stop filter applied");
    }

    @When("the user filters by airline {string}")
    public void filterByAirline(String airlineName) {
        resultsPage.filterByAirline(airlineName);
        logger.info("Airline filter applied: {}",
            airlineName);
    }

    @And("the user clears all filters")
    public void clearAllFilters() {
        resultsPage.clearAllFilters();
        logger.info("All filters cleared");
    }

    @Then("the flight results should be updated")
    public void flightResultsShouldBeUpdated() {
        int afterCount =
            resultsPage.getCurrentResultCount();
        logger.info("After filter count: {}", afterCount);

        // Results must still have at least 1 flight
        Assert.assertTrue(afterCount > 0,
            "No flights shown after filter. " +
            "Filter may have removed all results."
        );

        logger.info("Filter applied successfully. " +
            "Before: {} | After: {}",
            resultCountBeforeFilter, afterCount);
    }
}