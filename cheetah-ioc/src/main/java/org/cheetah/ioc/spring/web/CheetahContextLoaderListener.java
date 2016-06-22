package org.cheetah.ioc.spring.web;


import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.BeanFactoryProvider;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * Created by Max on 2016/5/4.
 */
public class CheetahContextLoaderListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        BeanFactoryProvider provider = new SpringBeanFactoryProvider(applicationContext);
        BeanFactory.setBeanFactoryProvider(provider);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }
}
