package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.SearchResultsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class SearchResultsStepDefs {

    private static final Logger logger =
        LogManager.getLogger(SearchResultsStepDefs.class);

    private final SearchResultsPage resultsPage =
        new SearchResultsPage();

    // ─────────────────────────────────────────
    //  PAGE LOAD
    // ─────────────────────────────────────────

    @Then("the search results page should be displayed")
    public void verifyResultsPageLoaded() {
        resultsPage.waitForResultsToLoad();
        Assert.assertTrue(
            resultsPage.isResultsPageLoaded(),
            "Results page not loaded. URL: " +
            resultsPage.getSearchUrl()
        );
    }

    // ─────────────────────────────────────────
    //  URL ASSERTIONS
    // ─────────────────────────────────────────

    @And("the search URL should contain {string} and {string}")
    public void urlShouldContainOriginAndDest(
            String origin, String dest) {
        logger.info("Verifying route in URL: {}-{}",
            origin, dest);
        Assert.assertTrue(
            resultsPage.isCorrectRoute(origin, dest),
            "Expected URL to contain: " + origin +
            " and " + dest +
            " | Actual URL: " + resultsPage.getSearchUrl()
        );
    }

    @And("the search URL should contain trip type {string}")
    public void urlShouldContainTripType(String tripType) {
        String url = resultsPage.getSearchUrl();
        logger.info("Checking trip type {} in URL: {}",
            tripType, url);
        Assert.assertTrue(
            url.contains(tripType),
            "Trip type '" + tripType +
            "' not found in URL: " + url
        );
    }

    // ─────────────────────────────────────────
    //  RESULTS COUNT
    // ─────────────────────────────────────────

    @And("the results page should have at least {int} flight")
    public void resultsShouldHaveAtLeastNFlights(
            int minCount) {
        int actual = resultsPage.getResultCount();
        logger.info("Expected min: {} | Actual: {}",
            minCount, actual);
        Assert.assertTrue(actual >= minCount,
            "Expected at least " + minCount +
            " flights. Found: " + actual
        );
    }

    // ─────────────────────────────────────────
    //  CARD VALIDATION
    // ─────────────────────────────────────────

    @And("all flights should have valid prices")
    public void allFlightsShouldHaveValidPrices() {
        Assert.assertTrue(
            resultsPage.allResultsHaveValidPrices(),
            "One or more flights have invalid prices"
        );
    }

    @And("all flights should have airline names")
    public void allFlightsShouldHaveAirlineNames() {
        Assert.assertTrue(
            resultsPage.allResultsHaveAirlineNames(),
            "One or more flights missing airline name"
        );
    }

    @And("the first flight price should be greater than {int}")
    public void firstFlightPriceGreaterThan(int minPrice) {
        int actual = resultsPage.getFirstFlightPrice();
        logger.info("First flight price: {}", actual);
        Assert.assertTrue(actual > minPrice,
            "Expected price > " + minPrice +
            " | Actual: " + actual
        );
    }

    @And("the first flight should have an airline name")
    public void firstFlightShouldHaveAirlineName() {
        String airline =
            resultsPage.getFirstFlightAirline();
        logger.info("First flight airline: {}", airline);
        Assert.assertFalse(airline.isEmpty(),
            "First flight has no airline name"
        );
    }

    @And("no results message should not be displayed")
    public void noResultsMessageAbsent() {
        Assert.assertTrue(
            resultsPage.isNoResultsMessageAbsent(),
            "No results message showing unexpectedly"
        );
    }
}