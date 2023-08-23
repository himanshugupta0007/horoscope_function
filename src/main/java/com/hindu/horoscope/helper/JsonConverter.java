package com.hindu.horoscope.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON Converter
 *
 * @author Himanshu Gupta
 * @since 16 Aug 2023
 */
public class JsonConverter {

   static Gson gson = new GsonBuilder().create();

    /**
     * Method to convert Object from JSON
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson (String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * Method to convert object to json
     *
     * @param src
     * @return
     */
    public static String toJson (Object src) {
        return gson.toJson(src);
    }

}
