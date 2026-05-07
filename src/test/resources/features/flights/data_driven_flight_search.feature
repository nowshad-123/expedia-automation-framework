@regression @flights @dataDriven
Feature: Data Driven Flight Search

  @dataDriven @oneWay @smoke
  Scenario: One way flight search from JSON data OW_001
    Given the flight data is loaded from JSON for testId "OW_001"
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

  @dataDriven @oneWay
  Scenario: One way flight search from JSON data OW_002
    Given the flight data is loaded from JSON for testId "OW_002"
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

  @dataDriven @oneWay
  Scenario: One way flight search from JSON data OW_003
    Given the flight data is loaded from JSON for testId "OW_003"
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

  @dataDriven @excel @oneWay
  Scenario: One way flight search from Excel data OW_001
    Given the flight data is loaded from Excel for testId "OW_001"
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