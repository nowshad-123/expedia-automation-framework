@result
Feature: Search Results Validation

  Background:
    Given the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Hyderabad"
    And the user enters destination city as "Bengaluru"
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
  Scenario: User sorts by Non Stop First
    When the user sorts by Non Stop First
    Then the Non Stop First sort tab should be active
    And at least 1 flight should be visible after sort

  @smoke
  Scenario: User sorts by Early Departure
    When the user sorts by Early Departure from dropdown
    And at least 1 flight should be visible after sort

  @smoke
  Scenario: User sorts by Late Departure
    When the user sorts by Late Departure from dropdown
    And at least 1 flight should be visible after sort