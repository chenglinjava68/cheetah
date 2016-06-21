package org.cheetah.bootstraps.jetty;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.cheetah.bootstraps.Bootstrap;
import org.cheetah.bootstraps.BootstrapException;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Servlet;
import java.util.concurrent.CountDownLatch;

/**
 * @author Max
 */
public class JettyBootstrap implements Bootstrap {

    public static final String PORT_KEY = "http.port";
    public static final String IDLE_TIMEOUT_KEY = "http.idle.timeout";
    public static final String CONTEXT_PATH_KEY = "http.context.path";
    public static final String ACCEPT_QUEUE_SIZE_KEY = "http.accept.queue.size";
    public static final String MIN_THREADS = "server.min.threads";
    public static final String MAX_THREADS = "server.man.threads";
    public static final String SERVER_DESCRIPTOR = "server.descriptor";

    public static final int DEFAULT_PORT = 8000;
    public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 512;
    public static final long DEFAULT_IDLE_TIMEOUT = 30000;
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final String DEFAULT_SERVER_DESCRIPTOR = "./webapp/WEB_INF/web.xml";

    private final Configuration configuration;
    private String applicationConfig = "classpath:META-INF/application.xml";

    private Server server;
    private ServerConnector serverConnector;
    private WebAppContext webAppContext;
    private Class<? extends Servlet> dispatcher;

    public JettyBootstrap() {
        this(new ConfigurationFactory().fromClasspath("/application.properties"));
    }

    public JettyBootstrap(Class<? extends Servlet> dispatcher) {
        this(new ConfigurationFactory().fromClasspath("/application.properties"));
        this.dispatcher = dispatcher;
    }

    public JettyBootstrap(Configuration configuration) {
        this.configuration = configuration;
    }

    public JettyBootstrap(Configuration configuration, String applicationConfig) {
        this.configuration = configuration;
        this.applicationConfig = applicationConfig;
    }

    public static JettyBootstrap cxf() {
        return new JettyBootstrap(CXFServlet.class);
    }
    
    public static JettyBootstrap jersey() {
        return new JettyBootstrap(CXFServlet.class);
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
                JettyBootstrap.this.stop();
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
            server = new Server(new QueuedThreadPool(configuration.getInt(MAX_THREADS, 256),
                    configuration.getInt(MIN_THREADS, Runtime.getRuntime().availableProcessors() * 2)));
            serverConnector = new ServerConnector(server);
            configServerConnector();

            webAppContext = new WebAppContext();
            configWebAppContext();

            server.addConnector(serverConnector);
            server.setHandler(webAppContext);
            server.start();
        } catch (Exception e) {
            throw new BootstrapException("jetty boot occurs error.", e);
        }
    }

    private void configServerConnector() {
        serverConnector.setReuseAddress(true);
        serverConnector.setPort(configuration.getInt(PORT_KEY, DEFAULT_PORT));
        serverConnector.setIdleTimeout(configuration.getLong(IDLE_TIMEOUT_KEY, DEFAULT_IDLE_TIMEOUT));
        serverConnector.setAcceptQueueSize(configuration.getInt(ACCEPT_QUEUE_SIZE_KEY, DEFAULT_ACCEPT_QUEUE_SIZE));
    }

    private void configWebAppContext() {
        webAppContext.setContextPath(configuration.getString(CONTEXT_PATH_KEY, DEFAULT_CONTEXT_PATH));    //设置上下文根路径
        webAppContext.addEventListener(new ContextLoaderListener());  //提供Spring支持能力
        webAppContext.setInitParameter("contextConfigLocation", applicationConfig);    //Spring配置文件位置
        webAppContext.addFilter(createEncodingFilter(), "/*", null);  //添加编码过滤器，解决中文问题
        webAppContext.addServlet(dispatcher, "/*"); //引入Apache CXF，提供Restful Web Service能力
        webAppContext.setDescriptor(configuration.getString(SERVER_DESCRIPTOR, DEFAULT_SERVER_DESCRIPTOR));
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
