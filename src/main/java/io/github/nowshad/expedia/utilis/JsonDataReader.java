package io.github.nowshad.expedia.utilis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nowshad.expedia.models.FlightDataWrapper;
import io.github.nowshad.expedia.models.FlightTestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonDataReader {

    private static final Logger logger =
        LogManager.getLogger(JsonDataReader.class);

    private static final String JSON_PATH =
        "src/test/resources/testdata/flights.json";

    private static FlightDataWrapper dataWrapper;

    // ─────────────────────────────────────────
    //  LOAD DATA — called once
    // ─────────────────────────────────────────

    static {
        loadData();
    }

    private static void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            dataWrapper = mapper.readValue(
                new File(JSON_PATH),
                FlightDataWrapper.class
            );
            logger.info("JSON test data loaded: {}",
                JSON_PATH);
            logger.info("OneWay: {} | RoundTrip: {} " +
                "| Negative: {}",
                dataWrapper.getOneWayFlights().size(),
                dataWrapper.getRoundTripFlights().size(),
                dataWrapper.getNegativeTests().size()
            );
        } catch (IOException e) {
            logger.error("Failed to load JSON data: {}",
                e.getMessage());
            throw new RuntimeException(
                "Cannot load test data from: " +
                JSON_PATH, e);
        }
    }

    // ─────────────────────────────────────────
    //  PUBLIC API
    // ─────────────────────────────────────────

    /**
     * Returns all one way flight test data.
     */
    public static List<FlightTestData> getOneWayFlights() {
        return dataWrapper != null
            ? dataWrapper.getOneWayFlights()
            : Collections.emptyList();
    }

    /**
     * Returns all round trip flight test data.
     */
    public static List<FlightTestData> getRoundTripFlights() {
        return dataWrapper != null
            ? dataWrapper.getRoundTripFlights()
            : Collections.emptyList();
    }

    /**
     * Returns all negative test data.
     */
    public static List<FlightTestData> getNegativeTests() {
        return dataWrapper != null
            ? dataWrapper.getNegativeTests()
            : Collections.emptyList();
    }

    /**
     * Returns one way flight by testId.
     * e.g. getOneWayFlightById("OW_001")
     */
    public static FlightTestData getOneWayFlightById(
            String testId) {
        return getOneWayFlights().stream()
            .filter(f -> f.getTestId().equals(testId))
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    "TestId not found: " + testId));
    }

    /**
     * Returns round trip flight by testId.
     */
    public static FlightTestData getRoundTripById(
            String testId) {
        return getRoundTripFlights().stream()
            .filter(f -> f.getTestId().equals(testId))
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    "TestId not found: " + testId));
    }

    /**
     * Returns negative test by testId.
     */
    public static FlightTestData getNegativeTestById(
            String testId) {
        return getNegativeTests().stream()
            .filter(f -> f.getTestId().equals(testId))
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    "TestId not found: " + testId));
    }

    private JsonDataReader() {}
}