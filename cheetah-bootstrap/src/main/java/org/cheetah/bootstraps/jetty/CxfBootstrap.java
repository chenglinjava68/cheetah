package org.cheetah.bootstraps.jetty;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * cxf基于jetty的boot
 * Created by Max on 2016/8/8.
 */
public class CxfBootstrap extends JettyBootstrap {
    public CxfBootstrap() {
        super();
    }

    public CxfBootstrap(String serverConfig) {
        super(serverConfig);
    }

    public CxfBootstrap(String applicationConfig, JettyServerConfig serverConfig) {
        super(applicationConfig, serverConfig, CXFServlet.class);
    }

    public CxfBootstrap(String serverConfig, String applicationConfig) {
        super(serverConfig, applicationConfig, CXFServlet.class);
    }


    @Override
    protected void setDispatcher() {
        contextHandler.addServlet(dispatcher, "/*");
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
