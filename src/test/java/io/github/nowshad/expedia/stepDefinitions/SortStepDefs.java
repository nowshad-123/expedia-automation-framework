package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.SearchResultsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class SortStepDefs {

    private static final Logger logger =
        LogManager.getLogger(SortStepDefs.class);

    private final SearchResultsPage resultsPage =
        new SearchResultsPage();

    // ─────────────────────────────────────────
    //  SORT ACTIONS
    // ─────────────────────────────────────────

    @When("the user sorts by Cheapest")
    public void sortByCheapest() {
        resultsPage.sortByCheapest();
        logger.info("Sorted by Cheapest");
    }

    @When("the user sorts by Non Stop First")
    public void sortByNonStopFirst() {
        resultsPage.sortByNonStopFirst();
        logger.info("Sorted by Non Stop First");
    }

    @When("the user sorts by Early Departure from dropdown")
    public void sortByEarlyDeparture() {
        resultsPage.sortByEarlyDeparture();
        logger.info("Sorted by Early Departure");
    }

    @When("the user sorts by Late Departure from dropdown")
    public void sortByLateDeparture() {
        resultsPage.sortByLateDeparture();
        logger.info("Sorted by Late Departure");
    }

    // ─────────────────────────────────────────
    //  SORT ASSERTIONS
    // ─────────────────────────────────────────

    @Then("the Cheapest sort tab should be active")
    public void cheapestTabShouldBeActive() {
        Assert.assertTrue(
            resultsPage.isSortActive("Cheapest"),
            "Cheapest sort tab is not active"
        );
    }

    @Then("the Non Stop First sort tab should be active")
    public void nonStopFirstTabShouldBeActive() {
        Assert.assertTrue(
            resultsPage.isSortActive("Non Stop First"),
            "Non Stop First sort tab is not active"
        );
    }

    @And("the first price should be less than " +
         "or equal to second price")
    public void firstPriceLessThanOrEqualToSecond() {
        int first =
            resultsPage.getFirstFlightPriceAfterSort();
        int second =
            resultsPage.getSecondFlightPriceAfterSort();

        logger.info("Price[0]: {} | Price[1]: {}",
            first, second);

        Assert.assertTrue(
            resultsPage.arePricesSortedLowToHigh(),
            "Prices not sorted Low to High. " +
            "First: " + first +
            " | Second: " + second
        );
    }

    @And("at least 1 flight should be visible after sort")
    public void atLeastOneFlightVisibleAfterSort() {
        int count = resultsPage.getCurrentResultCount();
        logger.info("Flights after sort: {}", count);
        Assert.assertTrue(count > 0,
            "No flights visible after sort"
        );
    }
}