# MakeMyTrip Flight Booking — Automation Framework

> Industry-level Cucumber + Selenium + Java BDD Framework
> for MakeMyTrip Flight Booking Module

## Tech Stack
- Java 21
- Selenium WebDriver 4.18
- Cucumber BDD 7.15
- TestNG 7.9
- Maven
- ExtentReports
- Log4j2

---

## 📌 What Is This Project?

This is a real-world automation framework built to
demonstrate industry-level QA engineering skills.
It automates the MakeMyTrip flight booking module
covering one-way search, round-trip search, date
picker automation, and traveller selection — end to end.

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21 | Programming language |
| Selenium WebDriver | 4.18 | Browser automation |
| Cucumber BDD | 7.15 | Behaviour driven development |
| TestNG | 7.9 | Test execution and reporting |
| Maven | 3.x | Build and dependency management |
| WebDriverManager | 5.8 | Automatic browser driver setup |
| ExtentReports | 5.1 | Rich HTML test reports |
| Log4j2 | 2.23 | Structured logging to file + console |

---

## 🎯 Key Design Decisions

### ThreadLocal WebDriver
Each thread gets its own isolated driver instance.
Enables safe parallel execution without test
interference. Foundation for Sprint 4 parallel setup.

### Page Component Model
Reusable UI components built separately from page
objects. `DatePickerComponent` and
`TravellersComponent` are shared across one-way
and round-trip flows without duplication.

### Dynamic Date Generation
`DateUtil.getFutureDate(n)` calculates dates relative
to today at runtime. No hardcoded dates that expire
and break tests after a few weeks.

### WaitStrategy Enum
VISIBLE, CLICKABLE, PRESENCE strategies make wait
intent explicit. No magic numbers or mixed implicit
and explicit waits anywhere in the framework.

### CommonStepDefs Pattern
Shared Cucumber steps defined once and reused across
all runners. Eliminates DuplicateStepDefinitionException
and keeps step definitions DRY.

### Stale Element Retry
`BasePage.click()` automatically retries 3 times on
`StaleElementReferenceException`. Handles React DOM
re-renders after tab clicks and page transitions.

---

## 🏷️ Tag Strategy

| Tag | Scenarios | When to Run |
|-----|-----------|-------------|
| `@sanity` | 3 | Every commit — fastest check |
| `@smoke` | 9 | Every PR merge — all critical paths |
| `@oneWay` | 4 | After one-way search changes |
| `@roundTrip` | 4 | After round-trip changes |
| `@flights` | 8 | After any flight module changes |
| `@e2e` | 8 | Before release — full flow check |
| `@regression` | 9 | Full suite — release validation |

---

## ▶️ How to Run

### Prerequisites
- Java 21 or higher
- Maven 3.x
- Firefox browser installed

### Clone the repository
```bash
git clone https://github.com/nowshad-123/expedia-automation-framework.git
cd expedia-automation-framework
```

### Run full suite
```bash
mvn test
```

### Run by tag
```bash
# Sanity — 2 scenarios — ~30 seconds
mvn test -Dcucumber.filter.tags="@sanity"

# Smoke — 8 scenarios — ~4 minutes
mvn test -Dcucumber.filter.tags="@smoke"

# One way flights only
mvn test -Dcucumber.filter.tags="@oneWay"

# Round trip only
mvn test -Dcucumber.filter.tags="@roundTrip"

# Full regression
mvn test -Dcucumber.filter.tags="@regression"
```

### Run on specific browser
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=chrome
```

### Run in headless mode
```bash
mvn test -Dheadless=true
```

---

## 📊 Test Coverage — Sprint 1

| Feature | Scenario | Tags | Status |
|---------|----------|------|--------|
| Sanity | Browser launch + page load | @sanity @smoke | ✅ Pass |
| One Way | Delhi → Mumbai + date + travellers | @oneWay @smoke @sanity @e2e | ✅ Pass |
| One Way | Bangalore → Chennai | @oneWay @smoke @e2e | ✅ Pass |
| One Way | Kolkata → Hyderabad | @oneWay @smoke @e2e | ✅ Pass |
| Round Trip | Delhi → Mumbai + return date | @roundTrip @smoke @sanity @e2e | ✅ Pass |
| Round Trip | Bangalore → Chennai | @roundTrip @smoke @e2e | ✅ Pass |
| Round Trip | Kolkata → Hyderabad | @roundTrip @smoke @e2e | ✅ Pass |

**Total: 8 scenarios — 8 passing — 0 failing**

---

## 📈 Sprint Progress

### ✅ Sprint 1 — Foundation + Smoke (Days 1–8)
- [x] Day 1 — Project setup, ConfigReader, Log4j2, Git
- [x] Day 2 — DriverFactory, DriverManager, ThreadLocal
- [x] Day 3 — BasePage, WaitStrategy, Explicit waits
- [x] Day 4 — HomePage POM, One Way search automation
- [x] Day 5 — DatePickerComponent, Dynamic date selection
- [x] Day 6 — TravellersComponent, SearchResultsPage, E2E
- [x] Day 7 — Round Trip, CommonStepDefs, Smoke suite
- [x] Day 8 — Retry analyzer, Code audit, Retrospective

### ⏳ Sprint 2 — Results Intelligence (Days 9–16)
### ⏳ Sprint 3 — Stability + Negative Testing (Days 17–22)
### ⏳ Sprint 4 — Scale + CI/CD + Portfolio (Days 23–28)

---

## 🐛 Bug Fixes Log

| Day | Issue | Root Cause | Fix Applied |
|-----|-------|------------|-------------|
| Day 2 | Expedia bot detection | Akamai blocks WebDriver | Switched to MakeMyTrip |
| Day 4 | StaleElementReferenceException | React DOM re-renders after tab click | Added retry loop in BasePage.click() |
| Day 4 | Edge ERR_HTTP2_PROTOCOL_ERROR | Edge blocks HTTP2 on MMT | Switched to Firefox as primary browser |
| Day 5 | Calendar loops forever | MMT renders May2026 without space | Normalize strings with replaceAll before compare |
| Day 6 | TravellersComponent wrong pattern | Assumed +/- buttons | Rebuilt with selector box click pattern |
| Day 7 | DuplicateStepDefinitionException | Same step in multiple classes | Extracted all shared steps to CommonStepDefs |

---

## 📁 Reports

After running tests, reports are generated at:

reports/
├── SprintReport.html      ← ExtentReports HTML
├── SparkReport.html       ← ExtentReports Spark
├── smoke-html-report.html ← Cucumber HTML
├── smoke-json-report.json ← Cucumber JSON
└── screenshots/           ← Auto-captured on failure

---

## 👤 Author

**Nowshad Shaik**
[GitHub Profile](https://github.com/nowshad-123)

---

## 📄 License

This project is built for portfolio and learning
purposes.

---

*Sprint 1 Complete ✅ — Sprint 2 starting next*
