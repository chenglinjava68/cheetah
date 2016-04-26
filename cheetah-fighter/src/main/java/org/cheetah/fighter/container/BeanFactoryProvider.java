package org.cheetah.fighter.container;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2016/2/21.
 */
public interface BeanFactoryProvider {
    <T> T getBean(Class<T> bean);

    <T> T getBean(Class<T> beanType, String bean);

    <T> Map<String, T> getBeansOfType(Class<T> beanType);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass);
}
