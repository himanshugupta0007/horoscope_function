package com.hindu.horoscope.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.hindu.horoscope.helper.HoroscopeConstants;
import com.hindu.horoscope.helper.JsonConverter;
import com.hindu.horoscope.helper.Utilities;
import com.hindu.horoscope.helper.ZodiacEnum;
import com.hindu.horoscope.model.externalResponse.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import software.amazon.awssdk.utils.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Implementation of Reading Horoscope Data
 *
 * @author Himanshu Gupta
 * @since 17 Aug 2023
 */
@Slf4j
public class ReadHoroscopeDataServiceImpl {


    /**
     * This method is responsible for Reading the Predictions for Zodiacs and Save in the Dynamo DB
     *
     * @return
     */
    public boolean readAndSaveZodiacPredictions (LambdaLogger logger) {
        logger.log("Reading Environment Variables" + "\n");
        List<String> locales =
                Utilities.getListFromVariables(System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_LOCALES));
        if (CollectionUtils.isNullOrEmpty(locales)) {
            logger.log("Empty Locales. Please check the system settings" + "\n");
        } else {
            locales.forEach(locale -> {
                logger.log("Starting Reading and Saving Data for Locale: " + locale + "\n");
                String apiKey = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_API_KEY);
                String baseURL = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_BASE_URL);
                for (ZodiacEnum zodiacEnum : ZodiacEnum.values()) {
                    try {
                        logger.log("Reading Predictions for Zodiac: " + zodiacEnum.getName() + "for Locale: " + locale +
                                           "\n");
                        APIResponse dailyResponse =
                                fetchDailyPredictionForZodiac(zodiacEnum.getValue(), apiKey, baseURL, locale);
                    } catch (Exception ex) {
                        logger.log("Exception Occurred while reading Zodiac Data" + ex);
                        break;
                    }
                }
            });
        }
        return false;
    }

    /**
     * This method fetches the Daily Prediction for provided Zodiac Value
     *
     * @param zodiacValue
     * @param apiKey
     * @param baseURL
     * @param locale
     * @return
     */
    private APIResponse fetchDailyPredictionForZodiac (int zodiacValue, String apiKey, String baseURL,
            String locale) throws URISyntaxException, IOException, ParseException {
        HttpGet httpGet = new HttpGet(baseURL);
        URI uri = new URIBuilder(httpGet.getUri()).addParameter(HoroscopeConstants.REQUEST_PARAM_TYPE,
                                                                HoroscopeConstants.REQUEST_PARAM_TYPE_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_SPLIT, HoroscopeConstants.REQUEST_PARAM_SPLIT_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_LOCALE, HoroscopeConstants.REQUEST_PARAM_SPLIT_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_ZODIAC, String.valueOf(zodiacValue))
                .addParameter(HoroscopeConstants.REQUEST_PARAM_LOCALE, locale)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_API_KEY, apiKey)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_SHOW_SAME,
                              HoroscopeConstants.REQUEST_PARAM_SHOW_SAME_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_DAILY_DATE, Utilities.getLocalDateInString()).build();
        httpGet.setUri(uri);
        String response = callApi(baseURL, httpGet);
        return JsonConverter.fromJson(response, APIResponse.class);
    }

    /**
     * @param apiUrl
     * @return
     */
    private String callApi (String apiUrl, HttpGet request) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        }
        return null;
    }
}
