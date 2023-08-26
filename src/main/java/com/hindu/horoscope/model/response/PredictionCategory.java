package com.hindu.horoscope.model.response;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * Model class for Prediction Catergory
 *
 * @author Himanshu Gupta
 * @since 16 Ag 2023
 */
@DynamoDbBean
public class PredictionCategory {
    private String type;
    private String color;
    private String percentage;
    private String data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
