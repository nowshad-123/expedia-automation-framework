@sanity
Feature: Framework Sanity Check

  
  Scenario: Browser launches and MakeMyTrip homepage loads
    Given the browser is open and homepage is loaded
    Then the page title should contain "MakeMyTrip"
    And the flight search tab should be visible