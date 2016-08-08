package org.cheetah.bootstraps.jetty;

import org.cheetah.configuration.Configuration;

/**
 * Created by Max on 2016/8/8.
 */
public class SpringMvcBootstrap extends JettyBootstrap {

    public SpringMvcBootstrap(String applicationConfig, JettyServerConfig serverConfig) {
        super(applicationConfig, serverConfig, null);
    }

    public SpringMvcBootstrap(Configuration configuration, String applicationConfig) {
        super(configuration, applicationConfig, null);
    }

    public SpringMvcBootstrap(String serverConfig, String applicationConfig) {
        super(serverConfig, applicationConfig, null);
    }
}
