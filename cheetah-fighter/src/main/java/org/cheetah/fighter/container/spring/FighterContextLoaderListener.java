package org.cheetah.fighter.container.spring;

import org.cheetah.fighter.container.BeanFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * Created by Max on 2016/5/9.
 */
public class FighterContextLoaderListener extends ContextLoaderListener {
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        SpringBeanFactoryProvider springProvider = new SpringBeanFactoryProvider(applicationContext);
        BeanFactory.setBeanFactoryProvider(springProvider);
    }

    public void contextDestroyed(ServletContextEvent event) {
        BeanFactory.setBeanFactoryProvider(null);
        super.contextDestroyed(event);
    }
}
