package io.github.nowshad.expedia.utilis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

    private static final Logger logger =
        LogManager.getLogger(DateUtil.class);

    // "May 2025" — matches MakeMyTrip month header exactly
    public static final DateTimeFormatter MMT_MONTH_FORMAT =
        DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

    // "Mon May 04 2026" — matches aria-label exactly
    // dd gives leading zero: 04, 15, 28
    public static final DateTimeFormatter MMT_ARIA_FORMAT =
        DateTimeFormatter.ofPattern("EEE MMM dd yyyy",
            Locale.ENGLISH);

    public static LocalDate getFutureDate(int daysFromToday) {
        LocalDate date = LocalDate.now().plusDays(daysFromToday);
        logger.info("Future date calculated: {} ({} days from today)",
            date, daysFromToday);
        return date;
    }

    public static String formatMonthYear(LocalDate date) {
        return date.format(MMT_MONTH_FORMAT);
    }

    public static String formatDay(LocalDate date) {
        return String.valueOf(date.getDayOfMonth());
    }

    private DateUtil() {}
}