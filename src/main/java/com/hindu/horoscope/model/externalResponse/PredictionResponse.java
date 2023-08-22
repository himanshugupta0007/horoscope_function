package com.hindu.horoscope.model.externalResponse;

import lombok.Data;

import java.util.List;

/**
 * Weekly Response Data
 *
 * @author Himanshu Gupta
 * @since 21 Aug 2023
 */
@Data
public class PredictionResponse {
    public String lucky_color;
    public String lucky_color_code;
    public List<Integer> lucky_number;
    public int week_number;
    public BotResponse bot_response;
    public String zodiac;
}
