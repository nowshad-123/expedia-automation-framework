# Sprint 2 Retrospective

## Sprint Goal
Build search results intelligence —
validate flight cards, filters, sorting,
and data driven testing.

## What Was Built

### Components
- FlightCardComponent — card data extraction
- FilterComponent — airline, stops, price filters
- SortComponent — tab and dropdown sorting
- Combined filter scenarios
- Data driven with JSON and Excel

### Test Coverage Added
- 3 results validation scenarios
- 4 filter scenarios
- 4 combined filter scenarios
- 4 sort scenarios
- 6 data driven scenarios (JSON + Excel)

## What Went Well
- Page Component Model pattern worked cleanly
- FilterComponent and SortComponent reuse
  BasePage utilities without duplication
- JSON data structure grouped by type
  scales easily for Sprint 3 negative tests
- Universal getByTestId() lookup pattern
  simplifies all future step definitions
- Headless mode issues resolved consistently
  using findElements over waitForElements

## What Was Challenging
- Price elements invisible in headless mode
  Required switching from visibilityOfAll
  to presenceOfAll and findElements
- filterScenarios not found in JSON
  Required universal getByTestId() lookup
- 169 duplicate scenarios from testng.xml
  Multiple runners covering same scenarios
  Fixed with targeted suite files

## What We Learned
- waitForElements fails in headless when
  elements are outside viewport
- Always use findElements for counting
  DOM elements — visibility not required
- JSON sections must match wrapper field
  names exactly — case sensitive
- Suite files must be carefully designed
  to prevent scenario duplication

## Sprint 2 Metrics
- Days: 6 (Day 9–14)
- Scenarios added: 21
- Total scenarios: 30
- Components built: 3 new
- Data readers: 2 (JSON + Excel)
- Blockers resolved: 4
- Flaky tests: 0

## Sprint 3 Focus
- Negative testing — invalid inputs
- Error message validation
- Same source destination handling
- No results route testing
- Stale element hardening
- Wait strategy audit