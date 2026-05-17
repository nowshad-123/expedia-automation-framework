@sort
Feature: Search Results Sort Validation

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
    And the user close popup
    Then the search results page should be displayed

  @smoke
  Scenario: User sorts flights by Cheapest
    When the user sorts by Cheapest
    Then the Cheapest sort tab should be active
    And the first price should be less than or equal to second price
    And at least 1 flight should be visible after sort

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