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
    public static final String ENVIRONMENT_VARIABLE_DYNAMO_DB_TABLE_NAME = "TABLE_NAME";
    public static final String ENVIRONMENT_VARIABLE_BASE_URL = "BASE_URL";
    public static final String ENVIRONMENT_VARIABLE_QUERY_GSI = "QUERY_GSI";
    public static final String ENVIRONMENT_VARIABLE_DYNAMO_URL = "DYNAMO_URL";
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

    public static final String DAILY_PREDICTION_ENDPOINT = "/daily-moon";
    public static final String WEEKLY_PREDICTION_ENDPOINT = "/weekly-moon";

    public static final String WEEKLY_MOCK_RESPONSE = "{\n" +
            "    \"status\": 200,\n" +
            "    \"response\": {\n" +
            "        \"lucky_color\": \"blood-red\",\n" +
            "        \"lucky_color_code\": \"#8A0303\",\n" +
            "        \"lucky_number\": [\n" +
            "            0,\n" +
            "            6\n" +
            "        ],\n" +
            "        \"week_number\": 34,\n" +
            "        \"bot_response\": {\n" +
            "            \"status\": {\n" +
            "                \"score\": 15,\n" +
            "                \"split_response\": \"You may have committed a silly error in your task and your entire team may suffer due to you. You will be shouted upon by seniors but you will have to maintain silence.\"\n" +
            "            },\n" +
            "            \"finances\": {\n" +
            "                \"score\": 62,\n" +
            "                \"split_response\": \"You are required to pay utmost attention to money transaction issues as there are chances that you may fail prey to money frauds.\"\n" +
            "            },\n" +
            "            \"relationship\": {\n" +
            "                \"score\": 51,\n" +
            "                \"split_response\": \"Your lover may not seem to understand your feelings this week. You may be too indecisive this week so it's better that you compromise with him or her.\"\n" +
            "            },\n" +
            "            \"career\": {\n" +
            "                \"score\": 75,\n" +
            "                \"split_response\": \"You will work with great clarity in your mind as you are now sure of what you want to do. Use this energy to do something good and big.\"\n" +
            "            },\n" +
            "            \"travel\": {\n" +
            "                \"score\": 18,\n" +
            "                \"split_response\": \"You will have to be careful while traveling as there is a possibility of theft or some minor inconvenience. To avoid such mishaps be more alert. \"\n" +
            "            },\n" +
            "            \"family\": {\n" +
            "                \"score\": 88,\n" +
            "                \"split_response\": \"There is a possibility that you will be needed in your family as your opinion is going to matter Your influence on your family members will increase.\"\n" +
            "            },\n" +
            "            \"friends\": {\n" +
            "                \"score\": 72,\n" +
            "                \"split_response\": \"You might have come back to your home town and all you parted friends and relatives will come to know about you and rejoin you.\"\n" +
            "            },\n" +
            "            \"health\": {\n" +
            "                \"score\": 51,\n" +
            "                \"split_response\": \"joint pain may give you trouble in doing your daily activities. your efficiency will be low and hence you will be disturbed.\"\n" +
            "            },\n" +
            "            \"total_score\": {\n" +
            "                \"score\": 60,\n" +
            "                \"split_response\": \"You will first feel hard times in the first few days of the week. As days pass you will convert all your failures to your advantage and make the biggest success streak which you will be proud of.\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"zodiac\": \"Taurus\"\n" +
            "    }\n" +
            "}";

    public static final String DAILY_RESPONSE = "{\n" +
            "    \"status\": 200,\n" +
            "    \"response\": {\n" +
            "        \"lucky_color\": \"blood-red\",\n" +
            "        \"lucky_color_code\": \"#8A0303\",\n" +
            "        \"lucky_number\": [\n" +
            "            16,\n" +
            "            8\n" +
            "        ],\n" +
            "        \"bot_response\": {\n" +
            "            \"physique\": {\n" +
            "                \"score\": 50,\n" +
            "                \"split_response\": \"Do not wear a horrific outfit today \"\n" +
            "            },\n" +
            "            \"status\": {\n" +
            "                \"score\": 74,\n" +
            "                \"split_response\": \"There is a chance that you would have to use your skill of talking if you want to buy your way through a negotiation with a dealer.\"\n" +
            "            },\n" +
            "            \"finances\": {\n" +
            "                \"score\": 88,\n" +
            "                \"split_response\": \"You may be planning to buy a car after taking a car loan. Then  shall be the best day to avail a loan at lesser interest rates.\"\n" +
            "            },\n" +
            "            \"relationship\": {\n" +
            "                \"score\": 46,\n" +
            "                \"split_response\": \"You may be embarrassed after an argument with your partner today as you will later realize that you had argued on a wrong point. So it is advisable not create any arguments today.\"\n" +
            "            },\n" +
            "            \"career\": {\n" +
            "                \"score\": 82,\n" +
            "                \"split_response\": \"Partnership business will be beneficial. Both you and partner shall receive a huge amount of profits specially if you are in the field of automobiles.\"\n" +
            "            },\n" +
            "            \"travel\": {\n" +
            "                \"score\": 97,\n" +
            "                \"split_response\": \"You might have to travel to a few places as your professional life shall command. But it shall be a benefiting trip for you as it is bound to give you success.\"\n" +
            "            },\n" +
            "            \"family\": {\n" +
            "                \"score\": 25,\n" +
            "                \"split_response\": \"Your family may need you to do a lot for them and you will have to toil a lot to fulfill their needs as it will seem that no one is actually satisfied with you.\"\n" +
            "            },\n" +
            "            \"friends\": {\n" +
            "                \"score\": 76,\n" +
            "                \"split_response\": \"There is a slight chance that you might be facing some problems at home but you need not panic as you might get the expected support from your friends.\"\n" +
            "            },\n" +
            "            \"health\": {\n" +
            "                \"score\": 98,\n" +
            "                \"split_response\": \"You will be extremely energetic and make take up physical tasks and complete them that you were not able to previously finish. It will be a good day for you.\"\n" +
            "            },\n" +
            "            \"total_score\": {\n" +
            "                \"score\": 80,\n" +
            "                \"split_response\": \"You will be very virtuous which make the people around you feel happy with you. As a sign of your virtue nature will act back with a kind act with small signs of good luck. \"\n" +
            "            }\n" +
            "        },\n" +
            "        \"zodiac\": \"Taurus\"\n" +
            "    }\n" +
            "}";


}
