# Expedia Flight Booking — Automation Framework

> Industry-level BDD Automation Framework built with Cucumber + Selenium + Java

## Tech Stack
- Java 21
- Selenium WebDriver 4.18
- Cucumber BDD 7.15
- TestNG 7.9
- Maven
- ExtentReports
- Log4j2

## Status

🚧 **Sprint 1 in progress** — Foundation + Smoke Automation

---

## Sprint Progress

### ✅ Sprint 1 — Foundation + Smoke (Day 1–8)

- [x] Day 1 — Framework skeleton, ConfigReader, log4j2
- [x] Day 2 — DriverFactory, DriverManager, Sanity test
- [x] Day 3 — BasePage, WaitStrategy, Explicit waits
- [x] Day 4 — HomePage POM, One Way flight search
- [x] Day 5 — DatePickerComponent, Dynamic date selection
- [x] Day 6 — TravellersComponent, SearchResultsPage, Full E2E
- [x] Day 7 — Round Trip search + Sprint 1 completion
- [ ] Day 8 — Sprint 1 review + smoke suite

## Test Coverage

| Scenario | Status |
| --- | --- |
| Framework sanity — page load | ✅ Passing |
| One way search — Delhi→Mumbai | ✅ Passing |
| One way search — Bangalore→Chennai | ✅ Passing |
| One way search — Kolkata→Hyderabad | ✅ Passing |
| Full E2E with date + travellers | ✅ Passing |

## 🐛 Notable Bug Fixes

| Day | Issue | Fix |
|-----|-------|-----|
| Day 2 | Expedia bot detection | Switched to MakeMyTrip |
| Day 4 | StaleElementReferenceException on oneWay button | Added retry loop in BasePage.click() |
| Day 4 | Edge ERR_HTTP2_PROTOCOL_ERROR | Switched to Firefox as primary |
| Day 5 | Calendar never reached target month | Normalized month string — MMT renders May2026 without space |
| Day 7 | Duplicate step definition error | Extracted CommonStepDefs |

---

### ⏳ Sprint 2 — Results Intelligence (Day 9–16)
### ⏳ Sprint 3 — Stability + Negative Testing (Day 17–22)
### ⏳ Sprint 4 — Scale + CI/CD + Portfolio (Day 23–28)

---
## Author
Nowshad Shaik — [GitHub Profile](https://github.com/nowshad-123)