package com.hindu.horoscope.model.externalResponse;

import lombok.Data;

/**
 * Category Response
 * @author Himanshu Gupta
 * @since 21 Aug 2023
 */
@Data
public class CategoryResponse {
    public int score;
    public String split_response;
}
