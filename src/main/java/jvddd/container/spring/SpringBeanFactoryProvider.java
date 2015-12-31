package jvddd.container.spring;

import jvddd.container.BeanFactoryProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by Max on 2015/12/31.
 */
public class SpringBeanFactoryProvider implements BeanFactoryProvider<ApplicationContext>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public ApplicationContext getBeanFactory() {
        return applicationContext;
    }

    @Override
    public <T> T getBean(Class<T> bean) {
        return this.applicationContext.getBean(bean);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String beanName) {
        return this.applicationContext.getBean(beanName, beanType);
    }

    @Override
    public <T> Map<String, T> getBeans(Class<T> beanType) {
        return this.applicationContext.getBeansOfType(beanType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
