package org.cheetah.commons.httpclient.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
