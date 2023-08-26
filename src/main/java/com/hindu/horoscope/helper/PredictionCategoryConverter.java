package com.hindu.horoscope.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hindu.horoscope.model.response.PredictionCategory;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PredictionCategoryConverter implements AttributeConverter<List<PredictionCategory>> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @param predictionCategories
     * @return
     */
    @Override
    public AttributeValue transformFrom(List<PredictionCategory> predictionCategories) {
        List<AttributeValue> predictionCategoryAttributes = predictionCategories.stream()
                .map(this::convertPredictionCategoryToAttributeValue)
                .collect(Collectors.toList());
        return AttributeValue.builder().l(predictionCategoryAttributes).build();
    }

    /**
     * Converts the Prediction Category
     *
     * @param predictionCategory
     * @return
     */
    private AttributeValue convertPredictionCategoryToAttributeValue(PredictionCategory predictionCategory) {
        Map<String, AttributeValue> predictionCategoryMap = new HashMap<>();
        predictionCategoryMap.put("type", AttributeValue.builder().s(predictionCategory.getType()).build());
        predictionCategoryMap.put("color", AttributeValue.builder().s(predictionCategory.getColor()).build());
        predictionCategoryMap.put("percentage", AttributeValue.builder().s(predictionCategory.getPercentage()).build());
        predictionCategoryMap.put("data", AttributeValue.builder().s(predictionCategory.getData()).build());
        return AttributeValue.builder().m(predictionCategoryMap).build();
    }

    /**
     * @param input
     * @return
     */
    @Override
    public List<PredictionCategory> transformTo(AttributeValue input) {
        try {
            return MAPPER.readValue(input.s(), new TypeReference<List<PredictionCategory>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    @Override
    public EnhancedType<List<PredictionCategory>> type() {
        return EnhancedType.listOf(PredictionCategory.class);
    }

    /**
     * @return
     */
    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}

