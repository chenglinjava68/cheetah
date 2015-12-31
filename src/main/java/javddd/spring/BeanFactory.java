package javddd.spring;

import java.util.Map;

/**
 * Created by Max on 2015/12/22.
 */
public interface BeanFactory {
    <T> T getBean(Class<T> bean);

    <T> T getBean(Class<T> beanType, String beanName);

    <T> Map<String, T> getBeans(Class<T> beanType);

}
