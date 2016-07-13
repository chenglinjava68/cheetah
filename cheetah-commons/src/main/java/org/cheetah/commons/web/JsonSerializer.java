package org.cheetah.commons.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Max
 * @date 2015/5/7
 */
public abstract class JsonSerializer {

    private final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    private JsonSerializer() {
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deserialize(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deserialize(byte[] json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
