package io.github.nowshad.expedia.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@smoke",
    plugin = {
        "pretty",
        "html:reports/smoke-report.html",
        "json:reports/smoke-report.json"
    },
    monochrome = true
)
public class SmokeRunner
    extends AbstractTestNGCucumberTests {}
