package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/flights" +
               "/data_driven_flight_search.feature",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@dataDriven",
    plugin = {
        "pretty",
        "html:reports/data-driven-report.html",
        "json:reports/data-driven-report.json"
    },
    monochrome = true
)
public class DataDrivenRunner
    extends AbstractTestNGCucumberTests {}