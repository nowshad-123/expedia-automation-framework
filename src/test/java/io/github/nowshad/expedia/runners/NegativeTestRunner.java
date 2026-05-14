package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features =
        "src/test/resources/features/negative",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@negative",
    plugin = {
        "pretty",
        "html:reports/negative-report.html",
        "json:reports/negative-report.json"
    },
    monochrome = true
)
public class NegativeTestRunner
    extends AbstractTestNGCucumberTests {}
