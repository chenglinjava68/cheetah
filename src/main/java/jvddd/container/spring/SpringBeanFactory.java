package jvddd.container.spring;

import jvddd.container.BeanFactory;

import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactory implements BeanFactory {
    private SpringBeanFactoryProvider factoryProvider;

    public void setFactoryProvider(SpringBeanFactoryProvider factoryProvider) {
        this.factoryProvider = factoryProvider;
    }

    @Override
    public <T> T getBean(Class<T> bean) {
        return this.factoryProvider.getBean(bean);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String bean) {
        return this.factoryProvider.getBean(beanType, bean);
    }

    @Override
    public <T> Map<String, T> getBeans(Class<T> beanType) {
        return this.factoryProvider.getBeans(beanType);
    }

}
