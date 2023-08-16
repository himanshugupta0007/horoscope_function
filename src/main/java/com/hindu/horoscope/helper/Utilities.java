package com.hindu.horoscope.helper;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Helper class provides utility functions
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@Slf4j
public class Utilities {

    /**
     * Returns the Start Date and End Date for provieded week number
     *
     * @param weekNumber
     * @param year
     * @return
     */
    public String getDatesInStringForWeek (int weekNumber, int year) {
        log.info("Fetching Start Date and End Date for Week {} and Year {}", weekNumber, year);
        LocalDate startDate = getStartDateOfWeek(year, weekNumber);
        LocalDate endDate = getEndDateOfWeek(year, weekNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MMMM, yyyy", Locale.ENGLISH);
        return startDate.format(formatter).concat("-").concat(endDate.format(formatter));
    }

    /**
     * Returns start of date for the week
     *
     * @param year
     * @param weekNumber
     * @return
     */
    private LocalDate getStartDateOfWeek (int year, int weekNumber) {
        TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
        return LocalDate.ofYearDay(year, 1).with(fieldISO, 1).plusWeeks(weekNumber - 1);
    }

    /**
     * Returns end date for the week
     *
     * @param year
     * @param weekNumber
     * @return
     */
    private LocalDate getEndDateOfWeek (int year, int weekNumber) {
        TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
        return LocalDate.ofYearDay(year, 1).with(fieldISO, 1).plusWeeks(weekNumber).minusDays(1);
    }
}
