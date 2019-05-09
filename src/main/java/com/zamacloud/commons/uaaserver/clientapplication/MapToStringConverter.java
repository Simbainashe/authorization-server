package com.zamacloud.commons.uaaserver.clientapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Fact S Musingarimi
 * 10/7/18
 * 3:32 PM
 */

public class MapToStringConverter implements AttributeConverter<HashMap, String> {

    @Override
    public String convertToDatabaseColumn(HashMap changes) {
        if (Objects.isNull(changes)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(changes);
    }

    @Override
    public HashMap convertToEntityAttribute(String s) {
        if (Objects.isNull(s)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(s, HashMap.class);
        } catch (Exception e) {
            return null;
        }

    }
}
