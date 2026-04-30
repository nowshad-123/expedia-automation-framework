package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.HomePage;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RoundTripStepDefs {

    private static final Logger logger =
        LogManager.getLogger(RoundTripStepDefs.class);

    private final HomePage homePage = new HomePage();

    // ── UNIQUE TO ROUND TRIP ──────────────────
    @And("the user selects Round Trip")
    public void selectRoundTrip() {
        homePage.selectRoundTrip();
        logger.info("Round Trip selected");
    }

    @And("the user selects return date as {int} days " +
         "from today with departure {int}")
    public void selectReturnDate(
            int returnDays, int departureDays) {
        logger.info("Return: {} days | Departure was: {} days",
            returnDays, departureDays);
        homePage.selectReturnDate(departureDays, returnDays);
    }
}