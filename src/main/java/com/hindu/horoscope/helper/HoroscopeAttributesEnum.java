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
    FINANCES("Finances", "धन-सम्पत्ति", "#a8edbe"),
    RELATIONSHIP("Relationship", "वैवाहिक जीवन", "#f0bf8b"),
    CAREER("Career", "कैरियर", "#e9f08b"),
    TRAVEL("Travel", "यात्रा", "#8baef0"),
    FAMILY("Family", "परिवार", "#ee8bf0"),
    HEALTH("Health", "स्वास्थ्य", "#2069e8");
    private final String name_en;
    private final String name_hi;
    private final String color;

    HoroscopeAttributesEnum(String name_en, String name_hi, String color) {
        this.color = color;
        this.name_en = name_en;
        this.name_hi = name_hi;
    }
}
