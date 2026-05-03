package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/results",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@results",
    plugin = {
        "pretty",
        "html:reports/results-report.html",
        "json:reports/results-report.json"
    },
    monochrome = true
)
public class ResultsRunner
    extends AbstractTestNGCucumberTests {}