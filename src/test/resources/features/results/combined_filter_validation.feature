@filters @combinedFilter
Feature: Combined Filter Validation

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

  @sanity @smoke
  Scenario: Apply Non Stop filter then verify results
    Given the user notes the current result count
    When the user applies Non Stop filter
    Then the flight results should be updated
    

  @smoke
  Scenario: Apply Non Stop and Airline filters together
    Given the user notes the current result count
    When the user applies Non Stop filter
    And the user filters by airline "IndiGo"
    Then the flight results should be updated
    And the combined filter result count should be greater than 0

  @smoke
  Scenario: Clear all filters restores full results
    Given the user notes the current result count
    When the user applies Non Stop filter
    And the user notes the filtered result count
    And the user clears all filters
    Then the result count should be restored

  @smoke
  Scenario: Apply One Stop filter then verify results
    Given the user notes the current result count
    When the user applies One Stop filter
    Then the flight results should be updated