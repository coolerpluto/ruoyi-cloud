package com.highgo.message.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class JsonUtils {

    protected static ObjectMapper O = new ObjectMapper();

    public JsonUtils() {
    }

    public static <T> T parse(String json, Class<T> t) {
        try {
            return O.readValue(json, t);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(String json, TypeReference<T> t) {
        try {
            return O.readValue(json, t);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode parse(String json) {
        try {
            return O.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toJsonString(T t) {
        try {
            return O.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toPrettyJsonString(T t) {
        try {
            return O.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toPrettyJsonString(String json) {
        try {
            JsonNode jsonNode = (JsonNode) O.readValue(json, JsonNode.class);
            return O.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        O.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
