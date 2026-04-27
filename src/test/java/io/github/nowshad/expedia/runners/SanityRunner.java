package io.github.nowshad.expedia.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features  = "src/test/resources/features",
    glue      = {"io.github.nowshad.expedia.hooks", "io.github.nowshad.expedia.stepDefinitions"},
    tags      = "@sanity",
    plugin    = {
        "pretty",
        "html:reports/cucumber-html-report.html",
        "json:reports/cucumber-json-report.json"
        
    },
    monochrome = true
)
public class SanityRunner extends AbstractTestNGCucumberTests {}


