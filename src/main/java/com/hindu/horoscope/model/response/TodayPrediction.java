package com.hindu.horoscope.model.response;

import com.hindu.horoscope.helper.PredictionCategoryConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;

import java.util.List;

/**
 * POJO For Today's Prediction
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@DynamoDbBean
public class TodayPrediction {
    private String date;
    private String luckyNumber;
    private String luckyColor;
    private List<PredictionCategory> predictions;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
