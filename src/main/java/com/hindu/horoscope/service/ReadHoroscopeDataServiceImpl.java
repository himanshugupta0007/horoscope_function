package com.hindu.horoscope.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.hindu.horoscope.helper.*;
import com.hindu.horoscope.model.externalResponse.APIResponse;
import com.hindu.horoscope.model.externalResponse.BotResponse;
import com.hindu.horoscope.model.externalResponse.CategoryResponse;
import com.hindu.horoscope.model.externalResponse.PredictionResponse;
import com.hindu.horoscope.model.response.PredictionCategory;
import com.hindu.horoscope.model.response.TodayPrediction;
import com.hindu.horoscope.model.response.WeeklyPrediction;
import com.hindu.horoscope.model.response.ZodiacModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.utils.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Reading Horoscope Data
 *
 * @author Himanshu Gupta
 * @since 17 Aug 2023
 */
@Slf4j
public class ReadHoroscopeDataServiceImpl {

    private static final String DAILY_PREDICTION_ENDPOINT = HoroscopeConstants.DAILY_PREDICTION_ENDPOINT;
    private static final String WEEKLY_PREDICTION_ENDPOINT = HoroscopeConstants.WEEKLY_PREDICTION_ENDPOINT;

    /**
     * This method is responsible for Reading the Predictions for Zodiacs and Save in the Dynamo DB
     *
     * @return true if successful, false otherwise
     */
    public boolean readAndSaveZodiacPredictions(LambdaLogger logger) {
        logger.log("Checking and deleting is any existing data is present for date:" +
                Utilities.getLocalDateInString("yyyy-MM-dd") + "\n");
        String dynamoDBTableName =
                System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_DYNAMO_DB_TABLE_NAME);
        String queryGSI = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_QUERY_GSI);
        String localDate = Utilities.getLocalDateInString("yyyy-MM-dd");
        DynamoDbClient dynamoDbClient = getDynamoDB();
        checkDataIsPresent(dynamoDBTableName, queryGSI, localDate, dynamoDbClient, logger);
        logger.log("Reading Environment Variables\n");

        List<String> locales =
                Utilities.getListFromVariables(System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_LOCALES));

        if (CollectionUtils.isNullOrEmpty(locales)) {
            logger.log("Empty Locales. Please check the system settings\n");
            throw new RuntimeException("Empty Locales. Please check the system settings");
        }

        List<ZodiacModel> zodiacModelList = new ArrayList<>();
        String apiKey = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_API_KEY);
        String baseURL = System.getenv(HoroscopeConstants.ENVIRONMENT_VARIABLE_BASE_URL);

        for (String locale : locales) {
            logger.log("Starting Reading and Saving Data for Locale: " + locale + "\n");
            for (ZodiacEnum zodiacEnum : ZodiacEnum.values()) {
                try {
                    logger.log("Reading Predictions for Zodiac: " + zodiacEnum.getName() + " for Locale: " + locale +
                            "\n");
                    APIResponse dailyResponse =
                            fetchPredictionForZodiac(DAILY_PREDICTION_ENDPOINT, zodiacEnum.getValue(), apiKey, baseURL, locale, true);
                    APIResponse weeklyResponse =
                            fetchPredictionForZodiac(WEEKLY_PREDICTION_ENDPOINT, zodiacEnum.getValue(), apiKey, baseURL, locale, false);
                    updateZodiacData(zodiacModelList, dailyResponse, weeklyResponse, zodiacEnum, locale);
                } catch (Exception ex) {
                    logger.log("Exception Occurred while reading Zodiac Data" + ex + "\n");
                    throw new RuntimeException("Exception Occurred while reading Zodiac Data" + ex);
                }
            }
        }
        if (!CollectionUtils.isNullOrEmpty(zodiacModelList) &&
                zodiacModelList.size() == (ZodiacEnum.values().length * locales.size())) {
            logger.log("Saving Zodiac data in DB");
            saveDataToDynamoDB(dynamoDbClient, dynamoDBTableName, zodiacModelList);
        }
        return true;
    }

    /**
     * This method saves data to dynamo db table
     *
     * @param dynamoDbClient
     * @param dynamoDBTableName
     * @param zodiacModelList
     */
    private void saveDataToDynamoDB(DynamoDbClient dynamoDbClient, String dynamoDBTableName,
                                    List<ZodiacModel> zodiacModelList) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        DynamoDbTable<ZodiacModel> zodiacDynamoDBTable =
                enhancedClient.table(dynamoDBTableName, TableSchema.fromBean(ZodiacModel.class));
        zodiacModelList.forEach(zodiacModel -> {
            zodiacDynamoDBTable.putItem(zodiacModel);
        });
    }

    /**
     * Check if data available, delete the same
     */
    private void checkDataIsPresent(String dynamoDBTableName, String queryGSI, String localDate,
                                    DynamoDbClient dynamoDbClient, LambdaLogger logger) {
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#zodiacDate", "zodiacDate");
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":dateValue", AttributeValue.builder().s(localDate).build());

        QueryRequest request = QueryRequest.builder()
                .tableName(dynamoDBTableName)
                .indexName(queryGSI)
                .keyConditionExpression("#zodiacDate = :dateValue")
                .expressionAttributeNames(expressionAttributesNames)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        QueryResponse queryResponse = dynamoDbClient.query(request);
        if (!CollectionUtils.isNullOrEmpty(queryResponse.items())) {
            logger.log("Found " + queryResponse.items().size() + "records for Date: " + localDate + "\n");
            queryResponse.items().forEach(response -> {
                HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
                keyToDelete.put("id", AttributeValue.builder()
                        .s(response.get("id").s())
                        .build());
                DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName(dynamoDBTableName).
                        key(keyToDelete).build();
                logger.log("Deleting Record with ID: " + response.get("id").s() + "\n");
                DeleteItemResponse delResponse = dynamoDbClient.deleteItem(deleteItemRequest);
                logger.log("Deleted SuccessFully");
            });
        }
    }

    /**
     * returns the DynamoDB Client
     *
     * @return
     */
    private DynamoDbClient getDynamoDB() {
        return DynamoDbClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    /**
     * This method creates the Zodiac Model and adds the same in List to be saved in Dynamo DB
     *
     * @param zodiacModelList
     * @param dailyResponse
     * @param weeklyResponse
     * @param zodiacEnum
     * @param locale
     */
    private void updateZodiacData(List<ZodiacModel> zodiacModelList, APIResponse dailyResponse,
                                  APIResponse weeklyResponse, ZodiacEnum zodiacEnum, String locale) {
        if (Objects.nonNull(dailyResponse) && Objects.nonNull(weeklyResponse)) {
            ZodiacModel zodiacModel = new ZodiacModel();
            zodiacModel.setId(UUID.randomUUID().toString());
            zodiacModel.setZodiac(zodiacEnum.getName());
            zodiacModel.setLocale(locale);
            zodiacModel.setZodiacId(zodiacEnum.getValue());
            zodiacModel.setZodiacDate(Utilities.getLocalDateInString("yyyy-MM-dd"));
            zodiacModel.setTodayPrediction(getTodaysPrediction(dailyResponse));
            zodiacModel.setWeeklyPrediction(getWeeklyPredictionResponse(weeklyResponse));
            zodiacModelList.add(zodiacModel);
            return;
        }
        throw new RuntimeException();
    }

    /**
     * Returns the Weekly's Prediction
     *
     * @param weeklyPredictionResponse
     * @return
     */
    private WeeklyPrediction getWeeklyPredictionResponse(APIResponse weeklyPredictionResponse) {
        WeeklyPrediction weeklyPrediction = new WeeklyPrediction();
        PredictionResponse predictionResponse = weeklyPredictionResponse.getResponse();
        weeklyPrediction.setWeek(Utilities.getDatesInStringForWeek(predictionResponse.getWeek_number(), LocalDate.now()
                .getYear()));
        weeklyPrediction.setLuckyColor(predictionResponse.lucky_color_code);
        weeklyPrediction.setLuckyNumber(predictionResponse.lucky_number.stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
        weeklyPrediction.setPredictions(addPredictionCategories(predictionResponse.getBot_response()));
        return weeklyPrediction;
    }

    /**
     * Returns the Today's Prediction
     *
     * @param dailyResponse
     * @return
     */
    private TodayPrediction getTodaysPrediction(APIResponse dailyResponse) {
        TodayPrediction todayPrediction = new TodayPrediction();
        PredictionResponse predictionResponse = dailyResponse.getResponse();
        todayPrediction.setDate(Utilities.getLocalDateInString("dd/MM/yyyy"));
        todayPrediction.setLuckyColor(predictionResponse.lucky_color_code);
        todayPrediction.setLuckyNumber(predictionResponse.lucky_number.stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
        todayPrediction.setPredictions(addPredictionCategories(predictionResponse.getBot_response()));
        return todayPrediction;
    }

    /**
     * Adds Prediction Response Attributes
     *
     * @param bot_response
     * @return
     */
    private List<PredictionCategory> addPredictionCategories(BotResponse bot_response) {
        List<PredictionCategory> predictionCategories = new ArrayList<>();
        if (Objects.nonNull(bot_response.getPhysique())) {
            predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getPhysique(), HoroscopeAttributesEnum.PHYSIQUE));
        }
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getCareer(), HoroscopeAttributesEnum.CAREER));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getFamily(), HoroscopeAttributesEnum.FAMILY));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getHealth(), HoroscopeAttributesEnum.HEALTH));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getFinances(), HoroscopeAttributesEnum.FINANCES));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getStatus(), HoroscopeAttributesEnum.STATUS));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getRelationship(), HoroscopeAttributesEnum.RELATIONSHIP));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getTravel(), HoroscopeAttributesEnum.TRAVEL));
        predictionCategories.add(createPredictionCategoryFromBotResponse(bot_response.getTotal_score(), HoroscopeAttributesEnum.OVERALL));
        return predictionCategories;
    }

    /**
     * Creates the Prediction Category
     *
     * @param botResponse
     * @param horoscopeAttribute
     * @return
     */
    private PredictionCategory createPredictionCategoryFromBotResponse(CategoryResponse botResponse,
                                                                       HoroscopeAttributesEnum horoscopeAttribute) {
        PredictionCategory predictionCategory = new PredictionCategory();
        predictionCategory.setType(horoscopeAttribute.getName());
        predictionCategory.setData(botResponse.getSplit_response());
        predictionCategory.setPercentage(String.valueOf(botResponse.getScore()));
        predictionCategory.setColor(horoscopeAttribute.getColor());
        return predictionCategory;
    }


    /**
     * This method fetches the Prediction for the provided Zodiac and prediction type
     *
     * @param endpoint
     * @param zodiacValue
     * @param apiKey
     * @param baseURL
     * @param locale
     * @param isDaily
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ParseException
     */
    private APIResponse fetchPredictionForZodiac(String endpoint, int zodiacValue, String apiKey, String baseURL,
                                                 String locale, boolean isDaily)
            throws URISyntaxException, IOException, ParseException {
        HttpGet httpGet = new HttpGet(baseURL + endpoint);
        URI uri = buildApiUri(httpGet.getUri(), zodiacValue, apiKey, locale, isDaily);
        httpGet.setUri(uri);
        String response = callApi(httpGet);
        return Objects.nonNull(response) ? JsonConverter.fromJson(response, APIResponse.class) : null;
//        return isDaily ? JsonConverter.fromJson(HoroscopeConstants.DAILY_RESPONSE, APIResponse.class) :
//                JsonConverter.fromJson(HoroscopeConstants.WEEKLY_MOCK_RESPONSE, APIResponse.class);
    }


    /**
     * Builds the API URI with required parameters
     *
     * @param baseUri
     * @param zodiacValue
     * @param apiKey
     * @param locale
     * @param isDaily
     * @return
     * @throws URISyntaxException
     */
    private URI buildApiUri(URI baseUri, int zodiacValue, String apiKey, String locale, boolean isDaily) throws
                                                                                                         URISyntaxException {
        String predictionAttribute;
        String predictionValue;

        if (isDaily) {
            predictionAttribute = HoroscopeConstants.REQUEST_PARAM_DAILY_DATE;
            predictionValue = Utilities.getLocalDateInString("dd/MM/yyyy");
        } else {
            predictionAttribute = HoroscopeConstants.REQUEST_PARAM_WEEK;
            predictionValue = HoroscopeConstants.REQUEST_PARAM_WEEK_VALUE;
        }
        return new URIBuilder(baseUri)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_TYPE, HoroscopeConstants.REQUEST_PARAM_TYPE_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_SPLIT, HoroscopeConstants.REQUEST_PARAM_SPLIT_VALUE)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_LOCALE, locale)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_ZODIAC, String.valueOf(zodiacValue))
                .addParameter(HoroscopeConstants.REQUEST_PARAM_API_KEY, apiKey)
                .addParameter(HoroscopeConstants.REQUEST_PARAM_SHOW_SAME, HoroscopeConstants.REQUEST_PARAM_SHOW_SAME_VALUE)
                .addParameter(predictionAttribute, predictionValue)
                .build();
    }


    /**
     * Calls the API
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private String callApi(HttpGet request) throws IOException, ParseException {
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
