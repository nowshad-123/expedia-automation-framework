package io.github.nowshad.expedia.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightTestData {

    private String testId;
    private String description;
    private String origin;
    private String destination;
    private int departureDays;
    private int returnDays;
    private int adults;
    private int children;
    private int infants;
    private String travelClass;
    private String expectedError;

    // ─────────────────────────────────────────
    //  GETTERS AND SETTERS
    // ─────────────────────────────────────────

    public String getTestId() { return testId; }
    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDepartureDays() {
        return departureDays;
    }
    public void setDepartureDays(int departureDays) {
        this.departureDays = departureDays;
    }

    public int getReturnDays() { return returnDays; }
    public void setReturnDays(int returnDays) {
        this.returnDays = returnDays;
    }

    public int getAdults() { return adults; }
    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() { return children; }
    public void setChildren(int children) {
        this.children = children;
    }

    public int getInfants() { return infants; }
    public void setInfants(int infants) {
        this.infants = infants;
    }

    public String getTravelClass() {
        return travelClass;
    }
    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String getExpectedError() {
        return expectedError;
    }
    public void setExpectedError(String expectedError) {
        this.expectedError = expectedError;
    }

    @Override
    public String toString() {
        return "FlightTestData{" +
            "testId='" + testId + '\'' +
            ", origin='" + origin + '\'' +
            ", destination='" + destination + '\'' +
            ", departureDays=" + departureDays +
            '}';
    }
}