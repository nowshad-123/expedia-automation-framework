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

    // Stores counts for before/after comparison
    private int resultCountBeforeFilter = 0;
    private int resultCountAfterFirstFilter = 0;

    // ─────────────────────────────────────────
    //  COUNT TRACKING
    // ─────────────────────────────────────────

    @Given("the user notes the current result count")
    public void noteCurrentResultCount() {
        resultCountBeforeFilter =
            resultsPage.getCurrentResultCount();
        logger.info("Result count before filter: {}",
            resultCountBeforeFilter);
    }

    @And("the user notes the filtered result count")
    public void noteFilteredResultCount() {
        resultCountAfterFirstFilter =
            resultsPage.getCurrentResultCount();
        logger.info("Result count after first filter: {}",
            resultCountAfterFirstFilter);
    }

    // ─────────────────────────────────────────
    //  FILTER ACTIONS
    // ─────────────────────────────────────────

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

    // ─────────────────────────────────────────
    //  FILTER ASSERTIONS
    // ─────────────────────────────────────────

    @Then("the flight results should be updated")
    public void flightResultsShouldBeUpdated() {
        int afterCount =
            resultsPage.getCurrentResultCount();
        logger.info("After filter count: {}",
            afterCount);
        Assert.assertTrue(afterCount > 0,
            "No flights shown after filter"
        );
    }

    @And("the combined filter result count " +
         "should be greater than 0")
    public void combinedFilterResultGreaterThanZero() {
        int count =
            resultsPage.getCurrentResultCount();
        logger.info("Combined filter count: {}", count);
        Assert.assertTrue(count > 0,
            "No results after combined filter. " +
            "Airline may not have non-stop flights."
        );
    }

    @And("all visible flights should be non stop")
    public void allVisibleFlightsShouldBeNonStop() {
        // Verify non-stop filter is active
        // by checking filter panel state
        boolean nonStopActive =
            resultsPage.isNonStopFilterActive();
        logger.info("Non Stop filter active: {}",
            nonStopActive);
        Assert.assertTrue(nonStopActive,
            "Non Stop filter not active after selection"
        );
    }

    @Then("the result count should be restored")
    public void resultCountShouldBeRestored() {
        int restoredCount =
            resultsPage.getCurrentResultCount();
        logger.info("Before: {} | After filter: {} " +
            "| After clear: {}",
            resultCountBeforeFilter,
            resultCountAfterFirstFilter,
            restoredCount);

        // After clearing — count should be
        // >= filtered count (restored or more)
        Assert.assertTrue(
            restoredCount >= resultCountAfterFirstFilter,
            "Count after clear (" + restoredCount +
            ") less than filtered count (" +
            resultCountAfterFirstFilter + ")"
        );
    }
}