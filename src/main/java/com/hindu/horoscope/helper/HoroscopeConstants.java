package com.hindu.horoscope.helper;

/**
 * Horoscope Constants
 *
 * @author Himanshu Gupta
 * @since 17 Aug 2023
 */
public class HoroscopeConstants {
    private HoroscopeConstants() {
    }

    public static final String ENVIRONMENT_VARIABLE_LOCALES = "HOROSCOPE_LOCALES";
    public static final String ENVIRONMENT_VARIABLE_API_KEY = "API_KEY";
    public static final String ENVIRONMENT_VARIABLE_BASE_URL = "BASE_URL";
    //Request Params for Request
    public static final String REQUEST_PARAM_ZODIAC = "zodiac";

    public static final String REQUEST_PARAM_SHOW_SAME = "show_same";
    public static final String REQUEST_PARAM_SHOW_SAME_VALUE = "true";

    public static final String REQUEST_PARAM_TYPE = "type";
    public static final String REQUEST_PARAM_TYPE_VALUE = "big";

    public static final String REQUEST_PARAM_SPLIT = "split";
    public static final String REQUEST_PARAM_SPLIT_VALUE = "true";

    public static final String REQUEST_PARAM_WEEK = "week";
    public static final String REQUEST_PARAM_WEEK_VALUE = "thisweek";

    public static final String REQUEST_PARAM_DAILY_DATE = "date";
    public static final String REQUEST_PARAM_LOCALE = "lang";
    public static final String REQUEST_PARAM_API_KEY = "api_key";


}
