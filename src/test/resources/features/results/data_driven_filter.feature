@filters @dataDriven
Feature: Data Driven Filter Scenarios

  @smoke
  Scenario: Data driven non stop filter FILTER_001
    Given the flight data is loaded from JSON for testId "FILTER_001"
    And the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin from test data
    And the user enters destination from test data
    And the user selects departure date from test data
    And the user selects travellers from test data
    And the user clicks on Search button
    Then the search results page should be displayed
    And the user applies filter from test data
    And the flight results should be updated


  Scenario: Data driven airline filter FILTER_002
    Given the flight data is loaded from JSON for testId "FILTER_002"
    And the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin from test data
    And the user enters destination from test data
    And the user selects departure date from test data
    And the user selects travellers from test data
    And the user clicks on Search button
    Then the search results page should be displayed
    And the user applies filter from test data
    And the flight results should be updated