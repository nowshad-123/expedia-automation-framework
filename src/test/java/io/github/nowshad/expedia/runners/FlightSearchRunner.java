package io.github.nowshad.expedia.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features  = "src/test/resources/features/flights",
    glue      = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags      = "@e2e",
    plugin    = {
        "pretty",
        "html:reports/flight-search-report.html",
        "json:reports/flight-search-report.json"
    },
    monochrome = true
)
public class FlightSearchRunner extends AbstractTestNGCucumberTests {}