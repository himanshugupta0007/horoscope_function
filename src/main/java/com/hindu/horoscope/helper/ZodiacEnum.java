package com.hindu.horoscope.helper;

import lombok.Getter;

/**
 * Zodiac Enums
 *
 * @author Himanshu Gupta
 * @since 21 Aug 2023
 */
@Getter
public enum ZodiacEnum {
    ARIES(1, "Aries"),
    TAURUS(2, "Taurus"),
    GEMINI(3, "Gemini"),
    CANCER(4, "Cancer"),
    LEO(5, "Leo"),
    VIRGO(6, "Virgo"),
    LIBRA(7, "Libra"),
    SCORPIO(8, "Scorpio"),
    SAGITTARIUS(9, "Sagittarius"),
    CAPRICORN(10, "Capricorn"),
    AQUARIUS(11, "Aquarius"),
    PISCES(12, "Pisces");

    private final int value;
    private final String name;

    ZodiacEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}

