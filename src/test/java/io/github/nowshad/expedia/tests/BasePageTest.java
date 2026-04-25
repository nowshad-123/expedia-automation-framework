package io.github.nowshad.expedia.tests;

import org.openqa.selenium.By;

//TEMPORARY — delete after verification
//Run this as a standalone main to confirm BasePage works
//before wiring into Cucumber scenarios


import io.github.nowshad.expedia.config.ConfigReader;
import io.github.nowshad.expedia.driver.DriverManager;
import io.github.nowshad.expedia.pages.BasePage;

public class BasePageTest {

 public static void main(String[] args) {
     DriverManager.initDriver();
     DriverManager.getDriver().get(ConfigReader.get("base.url"));

     BasePage basePage = new BasePage();
     basePage.waitForPageLoad();

     System.out.println("Title: " + basePage.getPageTitle());
     System.out.println("URL:   " + basePage.getCurrentUrl());

     boolean flightTabVisible = basePage.isDisplayed(
         By.cssSelector("#search_form_product_selector_flights_pictogram")
     );
     System.out.println("Flight tab visible: " + flightTabVisible);

     DriverManager.quitDriver();
 }
}
