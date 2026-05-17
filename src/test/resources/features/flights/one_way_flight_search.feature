@flights @oneWay
Feature: One Way Flight Search

  Background:
    Given the user is on MakeMyTrip homepage

   @smoke @sanity
  Scenario: User searches for a one way flight with all details
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "Delhi"
    And the user enters destination city as "Mumbai"
    And the user selects departure date as 7 days from today
    And the user selects 1 adults 0 children 0 infants in Economy class
    And the user clicks on Search button
    Then the search results page should be displayed
    And the search URL should contain "DEL" and "BOM"

  @smoke
  Scenario Outline: User searches one way for multiple routes
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects One Way trip
    And the user enters origin city as "<origin>"
    And the user enters destination city as "<destination>"
    And the user selects departure date as <days> days from today
    And the user selects <adults> adults <children> children <infants> infants in Economy class
    And the user clicks on Search button
    Then the search results page should be displayed

    Examples:
      | origin    | destination | days | adults | children | infants |
      | Delhi     | Mumbai      | 7    | 1      | 0        | 0       |
      | Bangalore | Chennai     | 10   | 2      | 0        | 0       |
      | Kolkata   | Hyderabad   | 14   | 1      | 0        | 0       |