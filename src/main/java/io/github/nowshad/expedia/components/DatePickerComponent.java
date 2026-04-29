package io.github.nowshad.expedia.components;

import io.github.nowshad.expedia.enums.WaitStrategy;
import io.github.nowshad.expedia.pages.BasePage;
import io.github.nowshad.expedia.utilis.DateUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.time.LocalDate;

public class DatePickerComponent extends BasePage {

    private static final Logger logger =
        LogManager.getLogger(DatePickerComponent.class);

    // ─────────────────────────────────────────
    //  LOCATORS — confirmed from your DevTools
    // ─────────────────────────────────────────

    // Calendar wrapper
    private final By calendarContainer = By.xpath(
        "//div[contains(@class,'DayPicker')]"
    );

    // Month header — "May 2025"
    // MakeMyTrip shows one or two month panels
    // [1] scopes to the LEFT panel only
    private final By monthHeader = By.xpath("(//div[contains(@class,'DayPicker-Caption')])[1]");

    // Next month arrow
    private final By nextMonthArrow = By.xpath(
        "//span[contains(@class,'DayPicker-NavButton--next')]"
    );

    // ─────────────────────────────────────────
    //  PUBLIC API
    // ─────────────────────────────────────────

    /**
     * Selects departure date N days from today.
     * Calendar must already be open before calling.
     * MakeMyTrip auto-opens calendar after destination
     * is selected — confirmed from manual exploration.
     */
    public void selectDepartureDate(int daysFromToday) {
        LocalDate targetDate =
            DateUtil.getFutureDate(daysFromToday);
        logger.info("Selecting departure date: {} " +
            "({} days from today)", targetDate, daysFromToday);
        selectDate(targetDate);
    }

    /**
     * Selects return date N days from today.
     * Only for round trip — calendar stays open
     * after departure selection.
     */
    public void selectReturnDate(int daysFromToday) {
        LocalDate targetDate =
            DateUtil.getFutureDate(daysFromToday);
        logger.info("Selecting return date: {} " +
            "({} days from today)", targetDate, daysFromToday);
        selectDate(targetDate);
    }

    // ─────────────────────────────────────────
    //  CORE LOGIC
    // ─────────────────────────────────────────

    private void selectDate(LocalDate targetDate) {
        // Wait for calendar to be visible
        waitForElement(calendarContainer, WaitStrategy.VISIBLE);
        logger.info("Calendar is open and visible");

        // Navigate to correct month
        navigateToMonth(targetDate);

        // Click the date
        clickDate(targetDate);

        logger.info("Successfully selected date: {}", targetDate);
    }

    // ─────────────────────────────────────────
    //  MONTH NAVIGATION
    // ─────────────────────────────────────────

    private void navigateToMonth(LocalDate targetDate) {
        String targetMonthYear =
            DateUtil.formatMonthYear(targetDate);
        logger.info("Target month: {}", targetMonthYear);

        int maxAttempts = 12;
        int attempts = 0;

        while (attempts < maxAttempts) {
            String currentMonth = getCurrentDisplayedMonth();
            logger.info("Current month displayed: {}",
                currentMonth);

            if (currentMonth.trim()
                    .equalsIgnoreCase(targetMonthYear)) {
                logger.info("Reached target month: {}",
                    targetMonthYear);
                return;
            }

            logger.info("Clicking next month arrow...");
            click(nextMonthArrow);
            attempts++;
        }

        throw new RuntimeException(
            "Failed to reach month: " + targetMonthYear +
            " after " + maxAttempts + " attempts. " +
            "Last displayed month: " + getCurrentDisplayedMonth()
        );
    }

    private String getCurrentDisplayedMonth() {
        try {
            return getText(monthHeader).trim();
        } catch (Exception e) {
            logger.warn("Month header read failed: {}",
                e.getMessage());
            // Fallback for alternate MMT layouts
            By fallback = By.xpath(
                "(//div[contains(@class,'months')]" +
                "//div[contains(@class,'caption')])[1]"
            );
            return getText(fallback).trim();
        }
    }

    // ─────────────────────────────────────────
    //  DATE CLICK — 3 STRATEGIES
    // ─────────────────────────────────────────

    private void clickDate(LocalDate targetDate) {
        logger.info("Clicking date: {}", targetDate);

        // ── Strategy 1: aria-label ────────────────────
        // Your DevTools confirmed: aria-label="Mon May 04 2026"
        // DateTimeFormatter "EEE MMM dd yyyy" produces
        // exactly this format with Locale.ENGLISH
        String ariaLabel =
            targetDate.format(DateUtil.MMT_ARIA_FORMAT);
        logger.info("Trying aria-label strategy: {}", ariaLabel);

        By byAria = By.xpath(
            "//div[@aria-label='" + ariaLabel + "']" +
            " | //td[@aria-label='" + ariaLabel + "']"
        );

        // ── Strategy 2: data-date ─────────────────────
        // Format: "2026-05-04"
        String dataDate = targetDate.toString();
        By byDataDate = By.xpath(
            "//div[@data-date='" + dataDate + "']" +
            " | //td[@data-date='" + dataDate + "']"
        );

        // ── Strategy 3: day number text ───────────────
        // Excludes outside + disabled cells
        String dayNum = DateUtil.formatDay(targetDate);
        By byDayNum = By.xpath(
            "//div[contains(@class,'DayPicker-Day')" +
            " and not(contains(@class,'DayPicker-Day--outside'))" +
            " and not(contains(@class,'DayPicker-Day--disabled'))" +
            " and normalize-space(text())='" + dayNum + "']"
        );

        // Try strategies in order
        if (tryClick(byAria, "aria-label=" + ariaLabel))
            return;
        if (tryClick(byDataDate, "data-date=" + dataDate))
            return;
        if (tryClick(byDayNum, "day-number=" + dayNum))
            return;

        // All 3 failed — give actionable error
        throw new RuntimeException(
            "Could not click date: " + targetDate + "\n" +
            "Tried: \n" +
            "  1. aria-label='" + ariaLabel + "'\n" +
            "  2. data-date='" + dataDate + "'\n" +
            "  3. day number text='" + dayNum + "'\n" +
            "Open DevTools → inspect the date cell → " +
            "check exact aria-label format and update " +
            "MMT_ARIA_FORMAT in DateUtil.java"
        );
    }

    private boolean tryClick(By locator, String strategyName) {
        try {
            waitForElement(locator, WaitStrategy.CLICKABLE);
            click(locator);
            logger.info("Date clicked via: {}", strategyName);
            return true;
        } catch (Exception e) {
            logger.warn("Strategy failed [{}]: {}",
                strategyName, e.getMessage());
            return false;
        }
    }
}