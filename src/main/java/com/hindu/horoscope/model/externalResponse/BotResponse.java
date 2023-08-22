package com.hindu.horoscope.model.externalResponse;

import lombok.Data;

/**
 * Bot Response Objects
 *
 * @author Himanshu Gupta
 * @since 21 Aug 2023
 */
@Data
public class BotResponse {
    public CategoryResponse physique;
    public CategoryResponse status;
    public CategoryResponse finances;
    public CategoryResponse relationship;
    public CategoryResponse career;
    public CategoryResponse travel;
    public CategoryResponse family;
    public CategoryResponse friends;
    public CategoryResponse health;
    public CategoryResponse total_score;
}
