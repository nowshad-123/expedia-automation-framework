# Sprint 1 Retrospective

## Sprint Goal
Build production-grade BDD automation framework
covering one-way and round-trip flight search
on MakeMyTrip.

## What Went Well
- Framework architecture is clean and layered
- ThreadLocal driver management ready for parallel
- Page Component Model reused across flows
- Dynamic date calculation prevents test expiry
- CommonStepDefs eliminated duplication

## What Was Challenging
- Expedia bot detection — switched to MakeMyTrip
- Edge HTTP2 protocol error — switched to Firefox
- MakeMyTrip month header renders without space
- TravellersComponent UI pattern different from expected
- Duplicate step definition errors across runners

## What I Learned
- Always explore manually before writing locators
- Live production sites have bot detection — use
  stable environments for automation
- React SPAs re-render DOM after interactions —
  stale element handling is essential
- Normalize strings before comparison — never assume
  whitespace format

## Sprint 1 Metrics
- Days: 8
- Scenarios automated: 8
- Scenarios passing: 8
- Flaky tests: 0 (after hardening)
- Blockers resolved: 6
- Files created: 18+

## Sprint 2 Focus
- Search results page deep validation
- Filter and sort automation
- Page Component Model expansion
- Data driven testing with JSON