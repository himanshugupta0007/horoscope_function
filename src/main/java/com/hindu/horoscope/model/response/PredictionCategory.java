package com.hindu.horoscope.model.response;

import lombok.Data;

/**
 * Model class for Prediction Catergory
 *
 * @author Himanshu Gupta
 * @since 16 Ag 2023
 */
@Data
public class PredictionCategory {
    private String type;
    private String color;
    private String percentage;
    private String data;
}
