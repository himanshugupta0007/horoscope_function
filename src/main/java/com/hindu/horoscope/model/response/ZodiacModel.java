package com.hindu.horoscope.model.response;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

/**
 * Zodiac Model for Horoscope
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@DynamoDbBean
public class ZodiacModel {
    private String id;
    private String zodiac;
    private int zodiacId;
    private WeeklyPrediction weeklyPrediction;
    private TodayPrediction todayPrediction;
    private String zodiacDate;
    private String locale;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public int getZodiacId() {
        return zodiacId;
    }

    public void setZodiacId(int zodiacId) {
        this.zodiacId = zodiacId;
    }

    public WeeklyPrediction getWeeklyPrediction() {
        return weeklyPrediction;
    }

    public void setWeeklyPrediction(WeeklyPrediction weeklyPrediction) {
        this.weeklyPrediction = weeklyPrediction;
    }

    public TodayPrediction getTodayPrediction() {
        return todayPrediction;
    }

    public void setTodayPrediction(TodayPrediction todayPrediction) {
        this.todayPrediction = todayPrediction;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "DateGSI")
    public String getZodiacDate() {
        return zodiacDate;
    }


    public void setZodiacDate(String zodiacDate) {
        this.zodiacDate = zodiacDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }


}
