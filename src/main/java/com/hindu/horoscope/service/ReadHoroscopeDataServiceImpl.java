package com.hindu.horoscope.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.hindu.horoscope.helper.HoroscopeConstants;
import com.hindu.horoscope.helper.Utilities;
import com.hindu.horoscope.helper.ZodiacEnum;
import com.hindu.horoscope.model.externalResponse.APIResponse;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.utils.CollectionUtils;

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
    public static boolean readAndSaveZodiacPredictions(LambdaLogger logger) {
        logger.log("Reading Environment Variables" + "\n");
        List<String> locales =
                Utilities.getListFromVariables(System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_LOCALES));
        String apiKey = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_API_KEY);
        String baseURL = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_BASE_URL);
        if (CollectionUtils.isNullOrEmpty(locales)) {
            logger.log("Empty Locales. Please check the system settings" + "\n");
            return false;
        } else {
            locales.forEach(locale -> {
                logger.log("Starting Reading and Saving Data for Locale: " + locale + "\n");
                for (ZodiacEnum zodiacEnum : ZodiacEnum.values()) {
                    logger.log(
                            "Reading Predictions for Zodiac: " + zodiacEnum.getName() + "for Locale: " + locale + "\n");
                }
            });
        }
        return true;
    }
}
