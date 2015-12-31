package jvddd.container.spring;

import jvddd.container.BeanFactory;

import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactory implements BeanFactory {

    @Override
    public <T> T getBean(Class<T> bean) {
        return SpringBeanFactoryProvider.getBeanFactory().getBean(bean);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String bean) {
        return SpringBeanFactoryProvider.getBeanFactory().getBean(beanType, bean);
    }

    @Override
    public <T> Map<String, T> getBeans(Class<T> beanType) {
        return SpringBeanFactoryProvider.getBeanFactory().getBeansOfType(beanType);
    }

}
