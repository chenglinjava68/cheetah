package jvddd.spring;

import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Created by Max on 2015/12/31.
 */
public class SpringBeanFactoryProvider implements BeanFactoryProvider<ApplicationContext> {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
}
