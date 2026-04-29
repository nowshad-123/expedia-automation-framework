@flights @smoke
Feature: One Way Flight Search

  Background:
    Given the user is on MakeMyTrip homepage

  @oneWay @sanity
  Scenario: User searches for a one way flight with date
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Delhi"
    And the user enters destination city as "Mumbai"
    And the user selects departure date as 7 days from today
    And the user clicks on Search button
    Then the search results page should be displayed

  @oneWay
  Scenario Outline: User searches one way flight for multiple routes
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "<origin>"
    And the user enters destination city as "<destination>"
    And the user selects departure date as <days> days from today
    And the user clicks on Search button
    Then the search results page should be displayed

    Examples:
      | origin    | destination | days |
      | Delhi     | Mumbai      | 9    |
      | Bangalore | Chennai     | 10   |
      | Kolkata   | Hyderabad   | 14   |
