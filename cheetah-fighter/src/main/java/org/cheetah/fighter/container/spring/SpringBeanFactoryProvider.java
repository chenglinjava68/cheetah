package org.cheetah.fighter.container.spring;

import org.cheetah.fighter.container.BeanFactoryProvider;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactoryProvider implements BeanFactoryProvider {
    private ApplicationContext applicationContext;

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
}
