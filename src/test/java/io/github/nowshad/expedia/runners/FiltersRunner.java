package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/results",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@filters",
    plugin = {
        "pretty",
        "html:reports/filters-report.html",
        "json:reports/filters-report.json"
    },
    monochrome = true
)
public class FiltersRunner
    extends AbstractTestNGCucumberTests {}