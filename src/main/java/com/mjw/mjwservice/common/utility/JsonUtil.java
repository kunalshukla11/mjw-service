package com.mjw.mjwservice.common.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;


public class JsonUtil {

    private final ObjectMapper objectMapper;

    private JsonUtil() {
        objectMapper = new ObjectMapper();
    }

    public static JsonUtil getInstance() {
        return new JsonUtil();
    }

    @SneakyThrows
    public static String jsonStringRepresentation(final Object object) {
        return getInstance().objectMapper
                .writeValueAsString(object);
    }

    @SneakyThrows
    public static Object fromJsonString(final String json, final Class<?> clazz) {
        return getInstance().objectMapper
                .readValue(json, clazz);
    }

    public static <T> T fromJsonString(final String json, final TypeReference<T> typeReference)
            throws JsonProcessingException {
        return getInstance().objectMapper.readValue(json, typeReference); // Deserialize JSON string to
        // List<DashboardData>
    }

    @SneakyThrows
    public static <T> T fromObject(final Object value, final Class<T> clazz) {
        return getInstance().objectMapper
                .convertValue(value, clazz);
    }

    @SneakyThrows
    public static <T> T fromObject(final Object value, final TypeReference<T> typeReference) {
        return getInstance().objectMapper
                .convertValue(value, typeReference);
    }
}
