package org.cheetah.commons.httpclient.serializer;

import org.cheetah.commons.httpclient.ResourceSerializer;
import org.cheetah.commons.web.JsonSerializer;

/**
 * Created by Max on 2016/7/6.
 */
public class Jackson2JsonSerializer implements ResourceSerializer {
    @Override
    public String serialize(Object entity) {
        return JsonSerializer.serialize(entity);
    }

    @Override
    public <T> T deserialize(byte[] resource, Class<T> entity) {
        return JsonSerializer.deserialize(resource, entity);
    }

    @Override
    public <T> T deserialize(String resource, Class<T> entity) {
        return JsonSerializer.deserialize(resource, entity);
    }
}
