package jvddd.container.spring;

import jvddd.container.BeanFactory;
import jvddd.container.BeanFactoryProvider;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by Max on 2015/12/28.
 */
public class SpringBeanFactory implements BeanFactory {
    private BeanFactoryProvider<ApplicationContext> factoryProvider;

    public void setFactoryProvider(BeanFactoryProvider<ApplicationContext> factoryProvider) {
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
