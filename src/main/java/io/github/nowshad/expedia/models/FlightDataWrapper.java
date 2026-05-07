package io.github.nowshad.expedia.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDataWrapper {

    private List<FlightTestData> oneWayFlights;
    private List<FlightTestData> roundTripFlights;
    private List<FlightTestData> negativeTests;

    public List<FlightTestData> getOneWayFlights() {
        return oneWayFlights;
    }
    public void setOneWayFlights(
            List<FlightTestData> oneWayFlights) {
        this.oneWayFlights = oneWayFlights;
    }

    public List<FlightTestData> getRoundTripFlights() {
        return roundTripFlights;
    }
    public void setRoundTripFlights(
            List<FlightTestData> roundTripFlights) {
        this.roundTripFlights = roundTripFlights;
    }

    public List<FlightTestData> getNegativeTests() {
        return negativeTests;
    }
    public void setNegativeTests(
            List<FlightTestData> negativeTests) {
        this.negativeTests = negativeTests;
    }
}