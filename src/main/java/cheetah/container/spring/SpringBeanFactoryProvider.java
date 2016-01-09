package cheetah.container.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Max on 2015/12/31.
 */
public class SpringBeanFactoryProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getBeanFactory() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactoryProvider.applicationContext = applicationContext;
    }

}
