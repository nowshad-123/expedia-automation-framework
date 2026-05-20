# MakeMyTrip Flight Booking — Automation Framework

>End-to-end BDD Automation Framework for MakeMyTrip 
>
>Flight Booking Module built with industry-standard tools

[![MakeMyTrip Automation Suite](https://github.com/nowshad-123/expedia-automation-framework/actions/workflows/automation.yml/badge.svg)](https://github.com/nowshad-123/expedia-automation-framework/actions/workflows/automation.yml)

![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green)
![Cucumber](https://img.shields.io/badge/Cucumber-7.15-brightgreen)
![TestNG](https://img.shields.io/badge/TestNG-7.9-red)
![Maven](https://img.shields.io/badge/Maven-3.x-blue)
![Firefox](https://img.shields.io/badge/Browser-Firefox-orange)
---

## What This Project Demonstrates

This is a **portfolio-grade automation framework** built
to simulate real-world QA engineering practices across
4 sprints of Agile development. It covers the complete
flight booking user journey on MakeMyTrip — from search
form interaction to results validation, filtering,
sorting, and negative testing.

Every design decision mirrors what senior SDET teams
implement in production:

- **ThreadLocal WebDriver** for parallel-ready execution
- **Page Component Model** for reusable UI components
- **Data Driven Testing** from both JSON and Excel
- **BDD with Cucumber** for business-readable scenarios
- **CI/CD with GitHub Actions** for automated pipeline
- **Negative testing** for production-grade coverage

---

## Tech Stack
| Technology | Version | Purpose |
|------------|----------|----------|
| Java | 21 | Core programming language |
| Selenium WebDriver | 4.18 | Browser automation |
| Cucumber BDD | 7.15 | Behaviour-driven development |
| TestNG | 7.9 | Test execution and parallel execution |
| Maven | 3.x | Build and dependency management |
| WebDriverManager | 5.8 | Automatic browser driver management |
| ExtentReports | 5.1 | HTML test reporting |
| Log4j2 | 2.23 | Structured logging |
| Jackson Databind | 2.16 | JSON test data handling |
| Apache POI | 5.2 | Excel test data handling |
| GitHub Actions | — | CI/CD pipeline automation |


---

## Framework Architecture

```text
src/
├── main/java/io/github/nowshad/expedia/
│   ├── config/
│   │   └── ConfigReader.java
│   │       Reads config.properties with system
│   │       property override support for CI
│   │
│   ├── driver/
│   │   ├── DriverFactory.java
│   │   │   Chrome / Firefox / Edge creation
│   │   └── DriverManager.java
│   │       ThreadLocal storage — parallel safe
│   │
│   ├── pages/
│   │   ├── BasePage.java
│   │   │   All wait + interaction utilities
│   │   │   Stale element retry — 3 attempts
│   │   │   JS fallback click for overlays
│   │   ├── HomePage.java
│   │   │   Flight search form — origin, dest,
│   │   │   date, travellers, search
│   │   ├── SearchResultsPage.java
│   │   │   Results validation + delegation
│   │   └── NegativeTestPage.java
│   │       Error messages + no results page
│   │
│   ├── components/
│   │   ├── DatePickerComponent.java
│   │   │   Dynamic calendar navigation
│   │   │   3-strategy date click pattern
│   │   ├── TravellersComponent.java
│   │   │   Selector box pattern with
│   │   │   TravelClass enum
│   │   ├── FlightCardComponent.java
│   │   │   Price + airline extraction
│   │   ├── FilterComponent.java
│   │   │   Airline / stops / price filters
│   │   └── SortComponent.java
│   │       Tab + dropdown sort options
│   │
│   ├── models/
│   │   ├── FlightTestData.java
│   │   └── FlightDataWrapper.java
│   │
│   └── utils/
│       ├── JsonDataReader.java
│       ├── ExcelDataReader.java
│       ├── DateUtil.java
│       └── FrameworkConstants.java
│
└── test/
├── java/io/github/nowshad/expedia/
│   ├── hooks/
│   │   └── Hooks.java
│   ├── runners/
│   │   ├── SanityRunner.java
│   │   ├── FlightSearchRunner.java
│   │   ├── RoundTripRunner.java
│   │   ├── ResultsRunner.java
│   │   ├── FiltersRunner.java
│   │   ├── SortRunner.java
│   │   ├── DataDrivenRunner.java
│   │   ├── NegativeTestRunner.java
│   │   ├── SmokeRunner.java
│   │   └── RegressionRunner.java
│   └── stepDefinitions/
│       ├── CommonStepDefs.java
│       ├── FlightSearchStepDefs.java
│       ├── RoundTripStepDefs.java
│       ├── SearchResultsStepDefs.java
│       ├── FilterStepDefs.java
│       ├── SortStepDefs.java
│       ├── DataDrivenStepDefs.java
│       └── NegativeTestStepDefs.java
│
└── resources/
├── features/
│   ├── sanity.feature
│   ├── flights/
│   │   ├── one_way_flight_search.feature
│   │   ├── round_trip_flight_search.feature
│   │   └── data_driven_flight_search.feature
│   ├── results/
│   │   ├── search_results_validation.feature
│   │   ├── filter_validation.feature
│   │   ├── combined_filter_validation.feature
│   │   ├── sort_validation.feature
│   │   └── data_driven_filter.feature
│   └── negative/
│       └── negative_test_scenarios.feature
├── testdata/
│   ├── flights.json
│   └── flights_testdata.xlsx
├── config/
│   └── config.properties
├── extent.properties
└── log4j2.xml
```
---

## Key Design Decisions

### 1. ThreadLocal WebDriver
Each thread gets its own isolated `WebDriver`
instance via `ThreadLocal<WebDriver>` in
`DriverManager`. This makes the framework
parallel-execution ready without any driver
sharing or race conditions.

### 2. Page Component Model
Reusable UI components are separated from page
objects. `DatePickerComponent`, `TravellersComponent`,
`FilterComponent`, and `SortComponent` are shared
across multiple page objects — write once, use
everywhere.

### 3. Dynamic Date Generation
`DateUtil.getFutureDate(n)` calculates dates
relative to today at runtime using `LocalDate`.
No hardcoded dates that expire and break tests.

### 4. Universal JSON Lookup
`JsonDataReader.getByTestId()` searches all JSON
sections — oneWayFlights, roundTripFlights,
filterScenarios, negativeTests — with one call.
Feature files reference a testId not raw data.

### 5. Defensive Click Strategy
`BasePage.click()` retries 3 times on
`StaleElementReferenceException` and auto-falls
back to JavaScript click on
`ElementClickInterceptedException`. Handles
React DOM re-renders and overlay issues.

### 6. WaitStrategy Enum
`VISIBLE`, `CLICKABLE`, `PRESENCE` strategies
make wait intent self-documenting. Set
`implicit.wait=0` — all waits are explicit
and controlled.

---

## Test Coverage

| Feature | Scenarios | Tags | Status |
|---------|-----------|------|--------|
| Framework Sanity | 1 | @sanity | ✅ |
| One Way Search | 4 | @oneWay | ✅ |
| Round Trip Search | 4 | @roundTrip | ✅ |
| Results Validation | 3 | @results | ✅ |
| Filter Validation | 4 | @filters | ✅ |
| Combined Filters | 4 | @combinedFilter | ✅ |
| Sort Validation | 4 | @sort | ✅ |
| Data Driven — JSON | 3 | @dataDriven | ✅ |
| Data Driven — Excel | 1 | @dataDriven | ✅ |
| Data Driven Filters | 2 | @dataDriven | ✅ |
| Negative — Same Airport | 2 | @negative | ✅ |
| Negative — No Results | 3 | @negative | ✅ |
| **Total** | **35** | | **✅** |

---

## Tag Strategy

| Tag | Scenarios | When to Run |
|-----|-----------|-------------|
| `@sanity` | 7 | Every commit |
| `@smoke` | 21 | Every PR merge |
| `@oneWay` | 4 | One way changes |
| `@roundTrip` | 4 | Round trip changes |
| `@results` | 3 | Results page changes |
| `@filters` | 8 | Filter changes |
| `@sort` | 4 | Sort changes |
| `@dataDriven` | 6 | Data reader changes |
| `@negative` | 5 | Negative test changes |
| `@regression` | 35 | Full release |

---

## How to Run

### Prerequisites
- Java 21+
- Maven 3.x
- Firefox browser

### Clone
```bash
git clone https://github.com/nowshad-123/expedia-automation-framework.git
cd expedia-automation-framework
```

### Run by tag
```bash
# Sanity — fastest feedback
mvn test -Dcucumber.filter.tags="@sanity"

# Smoke — critical paths
mvn test -Psmoke

# Specific feature
mvn test -Dcucumber.filter.tags="@oneWay"
mvn test -Dcucumber.filter.tags="@negative"
mvn test -Dcucumber.filter.tags="@dataDriven"

# Full regression
mvn test -Pregression
```

### Run in headless mode
```bash
mvn test -Dheadless=true \
         -Dcucumber.filter.tags="@sanity"
```

### Run full suite locally
```bash
# Windows
run_suite.bat

# Linux / Mac
./run_suite.sh
```

> **Note:** MakeMyTrip production uses Akamai
> Bot Manager enterprise protection. Running
> more than 7-8 sequential browser sessions
> triggers IP-level rate limiting. The full
> suite runs in tagged batches with a 60s gap.
> This mirrors real-world practice where teams
> test against staging environments with
> whitelisted IPs — not against live production.

---

## CI/CD Pipeline

Automated pipeline runs on every push:
```markdown
Push to `main` / `develop`
        ↓
GitHub Actions Triggered
   (Ubuntu Virtual Machine)
        ↓
Setup Java 21 + Firefox
        ↓
Run Smoke Tests
`mvn test -Psmoke` (Headless Mode)
        ↓
Generate Test Reports
        ↓
Upload Reports as Artifacts
```

### Pipeline Features
- Triggers on push to `main` and `develop`
- Triggers on every Pull Request to `main`
- Manual trigger with custom tag input
- Test reports downloadable from GitHub
- Screenshots captured on failure
- Maven dependency caching for speed

---

## Data Driven Testing

### JSON Structure
```json
{
  "oneWayFlights": [...],
  "roundTripFlights": [...],
  "filterScenarios": [...],
  "negativeTests": [...]
}
```

### Excel Structure
Sheet: `FlightData`
Columns: testId, tripType, origin, destination,
departureDays, returnDays, adults, children,
infants, travelClass

### Usage in Feature Files
```gherkin
Given the flight data is loaded from JSON
for testId "OW_001"
```

---

## Notable Engineering Decisions

### Why Firefox over Chrome
MakeMyTrip's Akamai Bot Manager blocks
Chrome WebDriver more aggressively than
Firefox on certain network configurations.
Firefox provides stable execution for this
test suite.

### Why Sequential over Parallel
MakeMyTrip production rate-limits multiple
simultaneous automated connections from the
same IP. The framework is architected for
parallel execution — `ThreadLocal` driver
is in place — but sequential batched runs
are used to avoid infrastructure-level
blocking.

### Why Dynamic Dates
All departure and return dates are calculated
at runtime using `LocalDate.now().plusDays(n)`.
Hardcoded dates expire and create silent test
failures. Dynamic dates need zero maintenance.

---

## Sprint History

| Sprint | Focus | Days | Scenarios |
|--------|-------|------|-----------|
| Sprint 1 | Foundation + Smoke | 1–8 | 9 |
| Sprint 2 | Results Intelligence | 9–14 | +21 |
| Sprint 3 | Stability + Negative | 15–16 | +5 |
| Sprint 4 | CI/CD + Scale | 17–18 | — |
| **Total** | | **18 days** | **35** |

---

## Release Tags

| Tag | Description |
|-----|-------------|
| v1.0-sprint1 | Foundation + smoke complete |
| v2.0-sprint2 | Results intelligence complete |
| v3.0-sprint3 | Stability + negative complete |

---

## Bug Fixes Log

| Day | Issue | Fix |
|-----|-------|-----|
| 2 | Expedia bot detection | Switched to MakeMyTrip |
| 4 | StaleElementReferenceException | Retry loop in BasePage.click() |
| 4 | Edge HTTP2 error | Firefox as primary browser |
| 5 | Calendar infinite loop | Month string normalization |
| 6 | Wrong traveller UI pattern | Selector box click pattern |
| 7 | Duplicate step definitions | CommonStepDefs extraction |
| 11 | Price elements invisible headless | findElements over waitForElements |
| 13 | JSON testId lookup failed | Universal getByTestId() |
| 15 | Calendar not opening for Leh | Explicit calendar open detection |
| 16 | hsBackDrop overlay in headless | JS overlay dismissal |

---

## Author

**Nowshad Shaik**
[GitHub](https://github.com/nowshad-123)

---

*Built as a portfolio project demonstrating
industry-level QA automation engineering
across 18 days of structured Agile sprints.*
