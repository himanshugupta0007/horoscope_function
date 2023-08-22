package com.hindu.horoscope.model.externalResponse;

import lombok.Data;

/**
 * Prediction Response
 *
 * @author Himanshu Gupta
 * @since 21 Aug 2023
 */
@Data
public class APIResponse {
    public int status;
    public PredictionResponse response;
}
