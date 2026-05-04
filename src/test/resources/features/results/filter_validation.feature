@regression @results @filters @smoke
Feature: Search Results Filter Validation

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
    Then the search results page should be displayed

  @filters @nonStop
  Scenario: User filters flights by Non Stop
    Given the user notes the current result count
    When the user applies Non Stop filter
    Then the flight results should be updated

  @filters @oneStop
  Scenario: User filters flights by One Stop
    Given the user notes the current result count
    When the user applies One Stop filter
    Then the flight results should be updated

  @filters @airline
  Scenario: User filters by specific airline
    Given the user notes the current result count
    When the user filters by airline "IndiGo"
    Then the flight results should be updated

  @filters @clearFilter
  Scenario: User clears all applied filters
    When the user applies Non Stop filter
    And the user clears all filters
    Then the flight results should be updated
