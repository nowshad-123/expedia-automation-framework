package io.github.nowshad.expedia.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/flights", glue = { "io.github.nowshad.expedia.hooks",
		"io.github.nowshad.expedia.stepDefinitions" }, tags = "@roundTrip ", plugin = { "pretty",
				"html:reports/round-trip-report.html", "json:reports/round-trip-report.json",
				"com.aventstack.extentreports.cucumber.adapter" + ".ExtentCucumberAdapter:" }, monochrome = true)
public class RoundTripRunner extends AbstractTestNGCucumberTests {
}
