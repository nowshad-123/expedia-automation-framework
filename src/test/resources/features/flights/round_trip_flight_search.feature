@regression @flights @roundTrip 
Feature: Round Trip Flight Search

  Background:
    Given the user is on MakeMyTrip homepage

   @smoke @sanity @e2e
  Scenario: User searches for a round trip flight
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects Round Trip
    And the user enters origin city as "Delhi"
    And the user enters destination city as "Mumbai"
    And the user selects departure date as 7 days from today
    And the user selects return date as 14 days from today with departure 7
    And the user selects 1 adults 0 children 0 infants in Economy class
    And the user clicks on Search button
    Then the search results page should be displayed
    And the search URL should contain "DEL" and "BOM"

  @smoke @e2e
  Scenario Outline: Round trip search for multiple routes
    When the user closes the login popup if present
    And the user clicks on Flights tab
    And the user selects Round Trip
    And the user enters origin city as "<origin>"
    And the user enters destination city as "<destination>"
    And the user selects departure date as <departure> days from today
    And the user selects return date as <return> days from today with departure <departure>
    And the user selects <adults> adults 0 children 0 infants in Economy class
    And the user clicks on Search button
    Then the search results page should be displayed

    Examples:
      | origin    | destination | departure | return | adults |
      | Delhi     | Mumbai      | 7         | 14     | 1      |
      | Bangalore | Chennai     | 10        | 17     | 2      |
      | Kolkata   | Hyderabad   | 14        | 21     | 1      |