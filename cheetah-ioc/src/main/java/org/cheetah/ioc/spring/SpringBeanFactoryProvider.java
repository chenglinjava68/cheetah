package org.cheetah.ioc.spring;

import org.cheetah.ioc.BeanFactoryProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactoryProvider implements BeanFactoryProvider, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public SpringBeanFactoryProvider() {
    }

    public SpringBeanFactoryProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T getBean(Class<T> bean) {
        return applicationContext.getBean(bean);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String bean) {
        return applicationContext.getBean(beanType, bean);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return applicationContext.getBeansOfType(beanType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return applicationContext.getBeansWithAnnotation(annoClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
