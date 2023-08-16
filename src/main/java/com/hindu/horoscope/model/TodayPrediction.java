package com.hindu.horoscope.model;

import lombok.Data;

import java.util.List;

/**
 * POJO For Today's Prediction
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@Data
public class TodayPrediction {
    private String date;
    private String luckyNumber;
    private String luckyColor;
    private List<PredictionCategory> predictions;
}
