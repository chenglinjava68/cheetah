package org.cheetah.commons.httpclient;

/**
 * 加=
 *
 * Created by Max on 2016/7/6.
 */
public interface EntitySerializer {
    /**
     * 将请求实体序列化
     * @param entity
     * @return
     */
    String serialize(Object entity);

    /**
     * 将资源表述转为对象实体
     * @param representation 资源表述
     * @param entity    实体
     * @param <T>   反序列化后的对象
     * @return
     */
    <T> T deserialize(String representation, Class<T> entity);

}
