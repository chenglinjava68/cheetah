package org.cheetah.bootstraps.jetty;

import org.cheetah.configuration.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Max on 2016/8/8.
 */
public class SpringMvcBootstrap extends JettyBootstrap {

    public SpringMvcBootstrap(String applicationConfig, JettyServerConfig serverConfig) {
        super(applicationConfig, serverConfig, DispatcherServlet.class);
    }

    public SpringMvcBootstrap(Configuration configuration, String applicationConfig) {
        super(configuration, applicationConfig, DispatcherServlet.class);
    }


}
