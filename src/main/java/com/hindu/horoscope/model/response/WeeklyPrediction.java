package com.hindu.horoscope.model.response;

import lombok.Data;

import java.util.List;

/**
 * POJO For Weekly Prediction
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
@Data
public class WeeklyPrediction {
    private String week;
    private String luckyNumber;
    private String luckyColor;
    private List<PredictionCategory> predictions;
}
