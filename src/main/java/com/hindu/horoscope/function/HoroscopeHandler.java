package com.hindu.horoscope.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hindu.horoscope.service.ReadHoroscopeDataServiceImpl;

import java.time.LocalDateTime;

/**
 * Entry point for Horoscope Function
 *
 * @author Himanshu Gupta
 * @since 17 Aug 2023
 */
public class HoroscopeHandler implements RequestHandler {

    ReadHoroscopeDataServiceImpl readHoroscopeDataService = new ReadHoroscopeDataServiceImpl();

    /**
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return
     */
    @Override
    public String handleRequest (Object input, Context context) {
        String responseMessage = null;
        LambdaLogger logger = context.getLogger();
        try {
            logger.log("Starting the Horoscope Function at:" + LocalDateTime.now() + "\n");
            logger.log("Initializing the Environment Variables\n");
            responseMessage = readHoroscopeDataService.readAndSaveZodiacPredictions(
                    logger) ? "Data Saved in DynamoDB Successfully" : "Data not saved. Please check the logs for troubleshooting";
            logger.log("Completed the Horoscope Function at:" + LocalDateTime.now() + "\n");
        } catch (Exception ex) {
            logger.log("Exceptions Occurred while Reading and Saving Predictions Data" + ex + "\n");
            responseMessage = ex.getMessage();
        }
        return responseMessage;
    }
}
