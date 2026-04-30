package io.github.nowshad.expedia.stepDefinitions;

import io.github.nowshad.expedia.pages.HomePage;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlightSearchStepDefs {

    private static final Logger logger =
        LogManager.getLogger(FlightSearchStepDefs.class);

    private final HomePage homePage = new HomePage();

    // ── UNIQUE TO ONE WAY ─────────────────────
    @And("the user selects One Way trip")
    public void selectOneWay() {
        homePage.selectOneWay();
        logger.info("One Way trip selected");
    }
}