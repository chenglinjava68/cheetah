package jvddd.spring;

/**
 * Created by Max on 2015/12/31.
 */
public interface BeanFactoryProvider<T> extends BeanFactory{
    T getBeanFactory();
}
