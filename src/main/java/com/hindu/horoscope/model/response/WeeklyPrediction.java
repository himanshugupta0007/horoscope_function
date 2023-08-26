package com.hindu.horoscope.model.response;

import com.hindu.horoscope.helper.PredictionCategoryConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;

import java.util.List;

/**
 * POJO For Weekly Prediction
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@DynamoDbBean
public class WeeklyPrediction {
    private String week;
    private String luckyNumber;
    private String luckyColor;
    private List<PredictionCategory> predictions;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public String getLuckyColor() {
        return luckyColor;
    }

    public void setLuckyColor(String luckyColor) {
        this.luckyColor = luckyColor;
    }

    @DynamoDbConvertedBy(PredictionCategoryConverter.class)
    public List<PredictionCategory> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionCategory> predictions) {
        this.predictions = predictions;
    }
}
