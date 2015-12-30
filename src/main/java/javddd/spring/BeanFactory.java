package javddd.spring;

/**
 * Created by Max on 2015/12/22.
 */
public interface BeanFactory {
    <T> T getBean(Class<T> bean);
    <T> T getBean(Class<T> beanType, String beanName);
}
