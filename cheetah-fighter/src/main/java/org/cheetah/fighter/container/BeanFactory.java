package org.cheetah.fighter.container;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2016/2/21.
 */
public class BeanFactory {
    private static BeanFactoryProvider beanFactoryProvider;

    public static <T> T getBean(Class<T> bean) {
        return beanFactoryProvider.getBean(bean);
    }

    public static <T> T getBean(Class<T> beanType, String bean) {
        return beanFactoryProvider.getBean(beanType, bean);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return beanFactoryProvider.getBeansOfType(beanType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return beanFactoryProvider.getBeansWithAnnotation(annoClass);
    }

    public static void setBeanFactoryProvider(BeanFactoryProvider provider) {
        beanFactoryProvider = provider;
    }
}
