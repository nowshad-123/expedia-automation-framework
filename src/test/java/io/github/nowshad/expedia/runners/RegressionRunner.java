package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "io.github.nowshad.expedia.hooks",
        "io.github.nowshad.expedia.stepDefinitions"
    },
    tags = "@regression",
    plugin = {
        "pretty",
        "html:reports/regression-report.html",
        "json:reports/regression-report.json",
        "com.aventstack.extentreports.cucumber" +
        ".adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class RegressionRunner
    extends AbstractTestNGCucumberTests {}