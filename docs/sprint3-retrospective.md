# Sprint 3 Retrospective

## Sprint Goal
Achieve framework stability through negative
testing, error validation, and systematic
hardening of all framework layers.

## What Was Built

### Negative Test Coverage
- Same airport error validation
- No results route handling (Leh, Shimla)
- Data driven negative scenarios
- NegativeTestPage with error locators

### Framework Hardening
- dismissHsBackDrop() for headless overlays
- dismissSpaOverlay() for promotional banners
- Calendar open detection for uncommon routes
- FrameworkConstants centralized constants
- waitForDomUpdate() shared across components
- Universal getByTestId() JSON lookup

## What Went Well
- Framework was already stable — no stale
  element issues in Sprint 3
- No Thread.sleep() anywhere outside
  waitForDomUpdate()
- Negative test locators confirmed accurately
  during manual exploration
- JSON data driven negative tests worked
  cleanly once expectedError text corrected

## What Was Challenging
- Calendar not auto-opening for less common
  routes like Leh and Shimla
  Fixed with explicit calendar open detection
- hsBackDrop overlay appearing in headless mode
  Fixed with JS dismissal in HomePage
- Wrong expectedError text in flights.json
  Always verify exact text from manual
  exploration before adding to test data
- Suite running 169 scenarios due to duplicate
  runners — fixed with targeted suite files

## What We Learned
- Never assume UI behavior is consistent
  across all routes — less popular routes
  behave differently
- Test data expected values must match
  EXACTLY what the UI shows — copy paste
  from DevTools, never type from memory
- Headless mode has additional overlays
  not present in headed mode — always
  test in headless before committing


## Sprint 3 Metrics
- Days: 2 (Day 15-16)
- Scenarios added: 5
- Total scenarios: 35
- Blockers resolved: 4
- Flaky tests: 0
- Thread.sleep outside waitForDomUpdate: 0

## Sprint 4 Focus
- Parallel execution
- Cross browser testing
- GitHub Actions CI/CD pipeline
- ExtentReports with screenshots
- Tag based execution profiles
- Final README and portfolio polish