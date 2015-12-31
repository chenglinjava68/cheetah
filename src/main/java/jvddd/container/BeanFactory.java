package jvddd.container;

import java.util.Map;

/**
 * Created by Max on 2015/12/22.
 */
public interface BeanFactory {
    <T> T getBean(Class<T> bean);

    <T> T getBean(Class<T> type, String name);

    <T> Map<String, T> getBeans(Class<T> type);

}
