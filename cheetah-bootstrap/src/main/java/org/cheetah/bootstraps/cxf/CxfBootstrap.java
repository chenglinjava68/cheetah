package org.cheetah.bootstraps.cxf;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.cheetah.bootstraps.Bootstrap;
import org.cheetah.bootstraps.BootstrapException;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.concurrent.CountDownLatch;

/**
 * @author yyang
 * @author siuming
 */
public class CxfBootstrap implements Bootstrap {

    public static final String PORT_KEY = "http.port";
    public static final String IDLE_TIMEOUT_KEY = "http.idle.timeout";
    public static final String CONTEXT_PATH_KEY = "http.context.path";
    public static final String ACCEPT_QUEUE_SIZE_KEY = "http.accept.queue.size";

    public static final int DEFAULT_PORT = 8000;
    public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 512;
    public static final long DEFAULT_IDLE_TIMEOUT = 30000;
    public static final String DEFAULT_CONTEXT_PATH = "/";

    private final Configuration configuration;

    private Server server;
    private ServerConnector serverConnector;
    private ServletContextHandler contextHandler;

    public CxfBootstrap() {
        this(new ConfigurationFactory().fromClasspath("/application.properties"));
    }

    public CxfBootstrap(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void bootstrap() {

        Loggers.me().warn(getClass(), "start bootstrap...");
        start();
        final CountDownLatch signal = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                signal.countDown();
                CxfBootstrap.this.stop();
            }
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    signal.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Loggers.me().error(getClass(), "signal await occurs error.", e);
                }
            }
        }.start();

        Loggers.me().warn(getClass(), "start bootstrap finished.");
    }

    private void start() {
        try {
            server = new Server();
            serverConnector = new ServerConnector(server);
            configServerConnector();

            contextHandler = new ServletContextHandler();
            configContextHandler();

            server.addConnector(serverConnector);
            server.setHandler(contextHandler);
            server.start();
        } catch (Exception e) {
            throw new BootstrapException("cxf boot occurs error.", e);
        }
    }

    private void configServerConnector() {
        serverConnector.setReuseAddress(true);
        serverConnector.setPort(configuration.getInt(PORT_KEY, DEFAULT_PORT));
        serverConnector.setIdleTimeout(configuration.getLong(IDLE_TIMEOUT_KEY, DEFAULT_IDLE_TIMEOUT));
        serverConnector.setAcceptQueueSize(configuration.getInt(ACCEPT_QUEUE_SIZE_KEY, DEFAULT_ACCEPT_QUEUE_SIZE));
    }

    private void configContextHandler() {
        contextHandler.setContextPath(configuration.getString(CONTEXT_PATH_KEY, DEFAULT_CONTEXT_PATH));    //设置上下文根路径
        contextHandler.addEventListener(new ContextLoaderListener());  //提供Spring支持能力
        contextHandler.setInitParameter("contextConfigLocation", "classpath:META-INF/application.xml");    //Spring配置文件位置
        contextHandler.addFilter(createEncodingFilter(), "/*", null);  //添加编码过滤器，解决中文问题
        contextHandler.addServlet(CXFServlet.class, "/*"); //引入Apache CXF，提供Restful Web Service能力
    }

    private FilterHolder createEncodingFilter() {
        FilterHolder encodingHolder = new FilterHolder();
        encodingHolder.setFilter(new CharacterEncodingFilter());
        encodingHolder.setInitParameter("encoding", "UTF-8");
        encodingHolder.setInitParameter("forceEncoding", "true");
        return encodingHolder;
    }

    private void stop() {
        if (null != server) {
            try {
                server.stop();
            } catch (Exception e) {
                Loggers.me().error(getClass(), "stop jetty server occurs error.", e);
            }
        }
    }
}
