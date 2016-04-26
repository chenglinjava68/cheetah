package org.cheetah.fighter.container.spring;

import org.cheetah.fighter.container.BeanFactoryProvider;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactoryProvider implements BeanFactoryProvider {

    @Override
    public <T> T getBean(Class<T> bean) {
        return SpringAplicationContextProvider.getApplicationContext().getBean(bean);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String bean) {
        return SpringAplicationContextProvider.getApplicationContext().getBean(beanType, bean);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return SpringAplicationContextProvider.getApplicationContext().getBeansOfType(beanType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return SpringAplicationContextProvider.getApplicationContext().getBeansWithAnnotation(annoClass);
    }
}
