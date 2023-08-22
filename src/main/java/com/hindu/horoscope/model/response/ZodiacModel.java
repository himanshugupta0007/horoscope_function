package com.hindu.horoscope.model.response;

import lombok.Data;

/**
 * Zodiac Model for Horoscope
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@Data
public class ZodiacModel {
    private String id;
    private String zodiac;
    private int zodiacId;
    private WeeklyPrediction weeklyPrediction;
    private TodayPrediction todayPrediction;
    private String date;
    private String locale;
}
