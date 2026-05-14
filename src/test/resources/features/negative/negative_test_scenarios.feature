@regression @negative
Feature: Negative Test Scenarios

  @negative @sameAirport @sanity
  Scenario: Same origin and destination shows error
    Given the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Delhi"
    And the user enters destination city as "Delhi"
    Then the same airport error should be displayed
    And the error message should contain "From & To airports cannot be the same"

  @negative @noResults @smoke
  Scenario: Search with no available flights
    Given the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Shimla"
    And the user enters destination city as "Bengaluru"
    And the user selects departure date as 7 days from today
    And the user selects 1 adults 0 children 0 infants in Economy class
    And the user clicks on Search button
    Then the no flights found page should be displayed
    And the no flights title should contain "Sorry, Flights Not Found"
    And the go back button should be visible

  @negative @sameAirport @dataDriven
  Scenario: Data driven same airport error NEG_001
    Given the flight data is loaded from JSON for testId "NEG_001"
    And the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin from test data
    And the user enters destination from test data
    Then the same airport error should be displayed
    And the error text should match expected from test data

  @negative @noResults @dataDriven
  Scenario: Data driven no results route NEG_002
    Given the flight data is loaded from JSON for testId "NEG_002"
    And the user is on MakeMyTrip homepage
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin from test data
    And the user enters destination from test data
    And the user selects departure date from test data
    And the user selects travellers from test data
    And the user clicks on Search button
    Then the no flights found page should be displayed
    And the no flights title should match expected from test data
