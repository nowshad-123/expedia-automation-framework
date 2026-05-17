@result
Feature: Search Results Validation

  Background:
    Given the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Delhi"
    And the user enters destination city as "Mumbai"
    And the user selects departure date as 7 days from today
    And the user selects 1 adults 0 children 0 infants in Economy class
    And the user clicks on Search button

  @smoke @sanity
  Scenario: Search results page loads with flight cards
    Then the search results page should be displayed
    And the results page should have at least 1 flight
    And all flights should have valid prices
    And all flights should have airline names

  @smoke
  Scenario: Search URL contains correct route details
    Then the search results page should be displayed
    And the search URL should contain "DEL" and "BOM"
    And the search URL should contain trip type "O"

  @smoke
  Scenario: First flight card has complete information
    Then the search results page should be displayed
    And the first flight price should be greater than 0
    And the first flight should have an airline name
    And no results message should not be displayed