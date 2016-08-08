package org.cheetah.bootstraps.jetty;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.cheetah.configuration.Configuration;
import org.eclipse.jetty.servlet.FilterHolder;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created by Max on 2016/8/8.
 */
public class CxfBootstrap extends JettyBootstrap {

    public CxfBootstrap(String applicationConfig, JettyServerConfig serverConfig) {
        super(applicationConfig, serverConfig, CXFServlet.class);
    }

    public CxfBootstrap(Configuration configuration, String applicationConfig) {
        super(configuration, applicationConfig, CXFServlet.class);
    }

    public CxfBootstrap(String serverConfig, String applicationConfig) {
        super(serverConfig, applicationConfig, CXFServlet.class);
    }

    @Override
    protected void setDispatcher() {
        webAppContext.addServlet(dispatcher, "/*");
    }

    @Override
    protected FilterHolder createEncodingFilter() {
        FilterHolder encodingHolder = new FilterHolder();
        encodingHolder.setFilter(new CharacterEncodingFilter());
        encodingHolder.setInitParameter("encoding", "UTF-8");
        encodingHolder.setInitParameter("forceEncoding", "true");
        return encodingHolder;
    }

}
