#!/bin/bash

echo "================================================"
echo "  MakeMyTrip Automation - Batched Execution"
echo "================================================"

run_suite() {
    local tag=$1
    local name=$2
    echo ""
    echo "Running: $name ($tag)"
    mvn test -Dcucumber.filter.tags="$tag"
    echo "Waiting 60s before next suite..."
    sleep 60
}

run_suite "@sanity"    "Sanity Tests"


echo ""
echo "================================================"
echo "  All suites complete."
echo "================================================"