package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/results",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@sort",
    plugin = {
        "pretty",
        "html:reports/sort-report.html",
        "json:reports/sort-report.json"
    },
    monochrome = true
)
public class SortRunner
    extends AbstractTestNGCucumberTests {}