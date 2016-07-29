package org.cheetah.commons.httpclient.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.cheetah.commons.httpclient.EntitySerializer;
import org.cheetah.commons.httpclient.HttpClientException;

import java.io.IOException;

/**
 * jackson序列化实现
 *
 * Created by Max on 2016/7/6.
 */
public class Jackson2JsonSerializer implements EntitySerializer {
    private final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    }

    @Override
    public String serialize(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new HttpClientException("jackson2JsonSerializer serialize failed.", e);
        }
    }

    @Override
    public <T> T deserialize(String representation, Class<T> entity) {
        try {
            return objectMapper.readValue(representation, entity);
        } catch (IOException e) {
            throw new HttpClientException("jackson2JsonSerializer deserialize failed.", e);
        }
    }
}
