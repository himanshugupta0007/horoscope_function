package com.hindu.horoscope.helper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.utils.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
     * This method returns the Local Date in DD/MM/YYYY format
     *
     * @return
     */
    public static String getLocalDateInString(String dateFormatterString) {
        LocalDate currentDate = LocalDate.now().plusDays(1l);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatterString);
        return currentDate.format(formatter);
    }

    /**
     * Returns the Start Date and End Date for provieded week number
     *
     * @param weekNumber
     * @param year
     * @return
     */
    public static String getDatesInStringForWeek(int weekNumber, int year) {
        log.info("Fetching Start Date and End Date for Week {} and Year {}", weekNumber, year);
        LocalDate startDate = getStartDateOfWeek(year, weekNumber);
        LocalDate endDate = getEndDateOfWeek(year, weekNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MMMM", Locale.ENGLISH);
        return startDate.format(formatter).concat(" - ").concat(endDate.format(formatter));
    }

    /**
     * Returns start of date for the week
     *
     * @param year
     * @param weekNumber
     * @return
     */
    private static LocalDate getStartDateOfWeek(int year, int weekNumber) {
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
    private static LocalDate getEndDateOfWeek(int year, int weekNumber) {
        TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
        return LocalDate.ofYearDay(year, 1).with(fieldISO, 1).plusWeeks(weekNumber).minusDays(1);
    }

    /**
     * Returns the list of values from the provided string value
     *
     * @param values
     * @return
     */
    public static List<String> getListFromVariables(String values) {
        log.info("Creating List of Environment Values");
        if (!StringUtils.isEmpty(values)) {
            if (values.contains(","))
                return Arrays.asList(values.split(","));
            else
                return Collections.singletonList(values);
        }
        return Collections.emptyList();
    }
}
