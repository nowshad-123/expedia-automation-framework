@sanity
Feature: Framework Sanity Check

  @smoke
  Scenario: Browser launches and Expedia homepage loads
    Given the browser is open and Expedia homepage is loaded
    Then the page title should contain "Expedia"
    And the flight search tab should be visible