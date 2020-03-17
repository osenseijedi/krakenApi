package com.ptrader.connector.kraken.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class JSONUtils {

    public static <T> String toString(T that){
        try {
            return JSONUtils.fromObjectToJsonString(that);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String fromObjectToJsonString(T that) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(that);
    }

    public static <T> T fromJsonStringToObject(String jsonString, Class<T> resultClass) throws IOException {
        return new ObjectMapper().readValue(jsonString, resultClass);
    }
}
