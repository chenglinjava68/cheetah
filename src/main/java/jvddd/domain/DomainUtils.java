package jvddd.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Max on 2016/1/9.
 */
public final class DomainUtils {
    public static <T> Class<T> getType(Class<?> clz, int index) {
        Type type = clz.getGenericSuperclass();

        Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();

        return (Class<T>) parameterizedType[index];
    }
}
