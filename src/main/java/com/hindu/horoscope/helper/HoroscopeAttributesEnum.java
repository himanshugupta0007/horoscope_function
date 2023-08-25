package com.hindu.horoscope.helper;

import lombok.Getter;

/**
 * Horoscope Attribute
 *
 * @author Himanshu Gupta
 * @since 25 Aug 2023
 */
@Getter
public enum HoroscopeAttributesEnum {
    PHYSIQUE("physique", "#D7a8eD"),
    STATUS("status", "#a8aaed"),
    FINANCES("finances", "#a8edbe"),
    RELATIONSHIP("relationship", "#f0bf8b"),
    CAREER("career", "#e9f08b"),
    TRAVEL("travel", "#8baef0"),
    FAMILY("family", "#ee8bf0"),
    HEALTH("health", "#2069e8"),
    OVERALL("total_score", "#91f2ef");
    private final String name;
    private final String color;

    HoroscopeAttributesEnum(String name, String color) {
        this.color = color;
        this.name = name;
    }
}
