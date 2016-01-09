package cheetah.container.spring;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactory {

    public static <T> T getBean(Class<T> bean) {
        return SpringBeanFactoryProvider.getBeanFactory().getBean(bean);
    }

    public static <T> T getBean(Class<T> beanType, String bean) {
        return SpringBeanFactoryProvider.getBeanFactory().getBean(beanType, bean);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return SpringBeanFactoryProvider.getBeanFactory().getBeansOfType(beanType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return SpringBeanFactoryProvider.getBeanFactory().getBeansWithAnnotation(annoClass);
    }
}
