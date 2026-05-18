@echo off
echo ================================================
echo   MakeMyTrip Automation - Batched Execution
echo ================================================

echo.
echo [1/7] Running Sanity Tests...
mvn test -Dcucumber.filter.tags="@sanity"
timeout /t 60 /nobreak

echo.
echo [2/7] Running One Way Flight Tests...
mvn test -Dcucumber.filter.tags="@oneWay"
timeout /t 60 /nobreak

echo.
echo [3/7] Running Round Trip Tests...
mvn test -Dcucumber.filter.tags="@roundTrip"
timeout /t 60 /nobreak

echo.
echo [4/7] Running Results Tests...
mvn test -Dcucumber.filter.tags="@result"
timeout /t 60 /nobreak

echo.
echo [5/7] Running Filter Tests...
mvn test -Dcucumber.filter.tags="@filters"
timeout /t 60 /nobreak

echo.
echo [6/7] Running Sort Tests...
mvn test -Dcucumber.filter.tags="@sort"
timeout /t 60 /nobreak

echo.
echo [7/7] Running Negative Tests...
mvn test -Dcucumber.filter.tags="@negative"

echo.
echo ================================================
echo   All suites complete.
echo ================================================
pause