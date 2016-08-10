package org.cheetah.bootstraps.jetty;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.thread.ThreadPool;
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

    public CxfBootstrap(String applicationConfig, JettyServerConfig serverConfig, ThreadPool threadPool) {
        super(applicationConfig, serverConfig, CXFServlet.class, threadPool);
    }

    public CxfBootstrap(String serverConfig, String applicationConfig, ThreadPool threadPool) {
        super(serverConfig, applicationConfig, CXFServlet.class, threadPool);
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
