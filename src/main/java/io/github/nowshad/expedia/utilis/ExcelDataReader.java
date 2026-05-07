package io.github.nowshad.expedia.utilis;

import io.github.nowshad.expedia.models.FlightTestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataReader {

    private static final Logger logger =
        LogManager.getLogger(ExcelDataReader.class);

    private static final String EXCEL_PATH =
        "src/test/resources/testdata/" +
        "flights_testdata.xlsx";

    private static final String SHEET_NAME =
        "FlightData";

    // ─────────────────────────────────────────
    //  PUBLIC API
    // ─────────────────────────────────────────

    /**
     * Returns all flight records from Excel.
     */
    public static List<FlightTestData> getAllFlights() {
        List<FlightTestData> flights = new ArrayList<>();
        try (FileInputStream fis =
                new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet =
                workbook.getSheet(SHEET_NAME);

            if (sheet == null) {
                throw new RuntimeException(
                    "Sheet not found: " + SHEET_NAME);
            }

            // Row 0 = header — skip it
            for (int i = 1;
                    i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                FlightTestData data =
                    mapRowToFlightData(row);
                flights.add(data);
                logger.info("Excel row {} loaded: {}",
                    i, data.getTestId());
            }

            logger.info("Total Excel records loaded: {}",
                flights.size());

        } catch (IOException e) {
            logger.error("Failed to read Excel: {}",
                e.getMessage());
            throw new RuntimeException(
                "Cannot read Excel: " + EXCEL_PATH, e);
        }
        return flights;
    }

    /**
     * Returns only OneWay flights from Excel.
     */
    public static List<FlightTestData> getOneWayFlights() {
        List<FlightTestData> result = new ArrayList<>();
        for (FlightTestData data : getAllFlights()) {
            if ("OneWay".equalsIgnoreCase(
                    data.getDescription())) {
                result.add(data);
            }
        }
        return result;
    }

    /**
     * Returns only RoundTrip flights from Excel.
     */
    public static List<FlightTestData> getRoundTripFlights() {
        List<FlightTestData> result = new ArrayList<>();
        for (FlightTestData data : getAllFlights()) {
            if ("RoundTrip".equalsIgnoreCase(
                    data.getDescription())) {
                result.add(data);
            }
        }
        return result;
    }

    /**
     * Returns flight by testId from Excel.
     */
    public static FlightTestData getFlightById(
            String testId) {
        return getAllFlights().stream()
            .filter(f -> f.getTestId().equals(testId))
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    "TestId not found in Excel: " +
                    testId));
    }

    // ─────────────────────────────────────────
    //  PRIVATE HELPERS
    // ─────────────────────────────────────────

    private static FlightTestData mapRowToFlightData(
            Row row) {
        FlightTestData data = new FlightTestData();

        // Column order matches Excel sheet
        data.setTestId(
            getCellValue(row, 0));       // testId
        data.setDescription(
            getCellValue(row, 1));       // tripType
        data.setOrigin(
            getCellValue(row, 2));       // origin
        data.setDestination(
            getCellValue(row, 3));       // destination
        data.setDepartureDays(
            getIntValue(row, 4));        // departureDays
        data.setReturnDays(
            getIntValue(row, 5));        // returnDays
        data.setAdults(
            getIntValue(row, 6));        // adults
        data.setChildren(
            getIntValue(row, 7));        // children
        data.setInfants(
            getIntValue(row, 8));        // infants
        data.setTravelClass(
            getCellValue(row, 9));       // travelClass

        return data;
    }

    private static String getCellValue(
            Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue()
                .trim();
            case NUMERIC -> String.valueOf(
                (int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(
                cell.getBooleanCellValue());
            default -> "";
        };
    }

    private static int getIntValue(
            Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> (int) cell
                .getNumericCellValue();
            case STRING -> {
                try {
                    yield Integer.parseInt(
                        cell.getStringCellValue()
                            .trim());
                } catch (NumberFormatException e) {
                    yield 0;
                }
            }
            default -> 0;
        };
    }

    private ExcelDataReader() {}
}