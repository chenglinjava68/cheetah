package org.cheetah.commons.httpclient.serializer;

import org.cheetah.commons.httpclient.EntitySerializer;
import org.cheetah.commons.web.JsonSerializer;

/**
 * Created by Max on 2016/7/6.
 */
public class Jackson2JsonSerializer implements EntitySerializer {
    @Override
    public String serialize(Object entity) {
        return JsonSerializer.serialize(entity);
    }

    @Override
    public <T> T deserialize(String representation, Class<T> entity) {
        return JsonSerializer.deserialize(representation, entity);
    }
}
