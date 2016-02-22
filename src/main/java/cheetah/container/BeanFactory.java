package cheetah.container;

import cheetah.container.spring.SpringBeanFactoryProvider;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by Max on 2016/2/21.
 */
public class BeanFactory {
    private BeanFactoryProvider provider;

    private static final BeanFactory BEAN_FACTORY = new BeanFactory();

    public static BeanFactory getBeanFactory() {
        return BEAN_FACTORY;
    }

    public BeanFactory() {
        provider = new SpringBeanFactoryProvider();
    }

    public BeanFactory(BeanFactoryProvider provider) {
        this.provider = provider;
    }

    public <T> T getBean(Class<T> bean) {
        return provider.getBean(bean);
    }

    public <T> T getBean(Class<T> beanType, String bean) {
        return provider.getBean(beanType, bean);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return provider.getBeansOfType(beanType);
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoClass) {
        return provider.getBeansWithAnnotation(annoClass);
    }

    public void setProvider(BeanFactoryProvider provider) {
        this.provider = provider;
    }
}
