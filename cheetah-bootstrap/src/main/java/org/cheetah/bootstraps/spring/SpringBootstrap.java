package org.cheetah.bootstraps.spring;

import org.cheetah.bootstraps.BootstrapSupport;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Max
 */
public class SpringBootstrap extends BootstrapSupport {

    private final String[] configLocations;
    private ClassPathXmlApplicationContext applicationContext;

    public SpringBootstrap(String... configLocations) {
        this.configLocations = configLocations;
    }

    @Override
    protected void startup() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext(configLocations);
        applicationContext.start();
        SpringBeanFactoryProvider springBeanFactoryProvider = new SpringBeanFactoryProvider(applicationContext);
        BeanFactory.setBeanFactoryProvider(springBeanFactoryProvider);
    }

    @Override
    protected void shutdown() {
        if (applicationContext != null) {
            applicationContext.stop();
            applicationContext.close();
        }
        super.shutdown();
    }

}
