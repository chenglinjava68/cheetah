package org.cheetah.commons.httpclient;

/**
 * Created by Max on 2016/7/6.
 */
public interface EntitySerializer {
    String serialize(Object entity);

    <T> T deserialize(String representation, Class<T> entity);

}
