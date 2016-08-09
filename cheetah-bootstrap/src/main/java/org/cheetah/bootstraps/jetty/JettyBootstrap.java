package org.cheetah.bootstraps.jetty;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.cheetah.bootstraps.BootstrapException;
import org.cheetah.bootstraps.BootstrapSupport;
import org.cheetah.common.logger.Err;
import org.cheetah.common.logger.Info;
import org.cheetah.common.utils.Objects;
import org.cheetah.common.utils.StringUtils;
import org.cheetah.configuration.Configuration;
import org.cheetah.configuration.ConfigurationFactory;
import org.cheetah.ioc.spring.web.CheetahContextLoaderListener;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static org.eclipse.jetty.http.HttpParser.LOG;

/**
 * @author Max
 */
public class JettyBootstrap extends BootstrapSupport {

    public static final String PORT_KEY = "jetty.http.port";
    public static final String IDLE_TIMEOUT_KEY = "jetty.http.idle.timeout";
    public static final String CONTEXT_PATH_KEY = "jetty.http.context.path";
    public static final String ACCEPT_QUEUE_SIZE_KEY = "jetty.http.accept.queue.size";
    public static final String MIN_THREADS = "jetty.server.min.threads";
    public static final String MAX_THREADS = "jetty.server.max.threads";
    public static final String SERVER_DESCRIPTOR = "jetty.server.web.xml";
    public static final String SERVER_WEBAPP_PATH = "jetty.server.webapp.path";

    public static final int DEFAULT_PORT = 8000;
    public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 512;
    public static final int DEFAULT_IDLE_TIMEOUT = 30000;
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final String DEFAULT_SERVER_DESCRIPTOR = "./webapp/WEB-INF/web.xml";
    public static final String DEFAULT_SERVER_WEBAPP_PATH = "./webapp";

    private static final String WEBXML = "WEB-INF/web.xml";

    private Configuration configuration;
    private String applicationConfig;
    protected JettyServerConfig serverConfig;
    private Server server;
    private ServerConnector connector;
    protected WebAppContext webAppContext;
    protected Class<? extends Servlet> dispatcher;
    private URI serverURI;

    public JettyBootstrap() {
        initialize();
    }

    public JettyBootstrap(String serverConfig) {
        this(serverConfig, null);
    }

    public JettyBootstrap(String serverConfig, String applicationConfig) {
        this(serverConfig, applicationConfig, null);
    }

    public JettyBootstrap(String applicationConfig, JettyServerConfig serverConfig, Class<? extends Servlet> dispatcher) {
        this.applicationConfig = applicationConfig;
        this.serverConfig = serverConfig;
        this.dispatcher = dispatcher;
        initialize();
    }

    public JettyBootstrap(String serverConfig, String applicationConfig, Class<? extends Servlet> dispatcher) {
        this.configuration = ConfigurationFactory.singleton().fromClasspath(serverConfig);
        this.applicationConfig = applicationConfig;
        this.dispatcher = dispatcher;
        initialize();
    }

    @Override
    protected void startup() throws Exception {
        try {
            server = new Server(new QueuedThreadPool(this.serverConfig.maxThreads(), this.serverConfig.minThreads()));
            ServerConnector connector = configServerConnector();
            server.addConnector(connector);

            configWebAppContext(getScratchDir());

            server.setHandler(webAppContext);
            server.start();

            this.serverURI = getServerUri(connector);
        } catch (Exception e) {
            throw new BootstrapException("jetty boot occurs error.", e);
        }
    }

    private void initialize() {
        if (Objects.isNull(this.serverConfig))
            if (Objects.isNull(this.configuration))
                loadEnvVariable();
            else
                loadConfigFile();

        webAppContext = new WebAppContext();
        Info.log(this.getClass(), "jetty server config initialize: {}", serverConfig.toString());
    }

    private void loadEnvVariable() {
        int maxThreads = Integer.parseInt(System.getProperty(MAX_THREADS, "256"));
        int minThreads = Integer.parseInt(System.getProperty(MIN_THREADS, (Runtime.getRuntime().availableProcessors() * 2) + ""));
        int port = Integer.parseInt(System.getProperty(PORT_KEY, DEFAULT_PORT + ""));
        int timeout = Integer.parseInt(System.getProperty(IDLE_TIMEOUT_KEY, DEFAULT_IDLE_TIMEOUT + ""));
        int acceptQueueSize = Integer.parseInt(System.getProperty(ACCEPT_QUEUE_SIZE_KEY, DEFAULT_ACCEPT_QUEUE_SIZE + ""));
        String contextPath = System.getProperty(CONTEXT_PATH_KEY, DEFAULT_CONTEXT_PATH);
        String descriptor = System.getProperty(SERVER_DESCRIPTOR, DEFAULT_SERVER_DESCRIPTOR);
        String webappPath = System.getProperty(SERVER_WEBAPP_PATH, DEFAULT_SERVER_WEBAPP_PATH);

        serverConfig = JettyServerConfig.newBuilder()
                .acceptQueueSize(acceptQueueSize)
                .contextPath(contextPath)
                .descriptor(descriptor)
                .maxThreads(maxThreads)
                .minThreads(minThreads)
                .port(port)
                .timeout(timeout)
                .webappPath(webappPath)
                .build();
    }

    private void loadConfigFile() {
        int maxThreads = configuration.getInt(MAX_THREADS, 256);
        int minThreads = configuration.getInt(MIN_THREADS, Runtime.getRuntime().availableProcessors() * 2);
        int port = configuration.getInt(PORT_KEY, DEFAULT_PORT);
        int timeout = configuration.getInt(IDLE_TIMEOUT_KEY, DEFAULT_IDLE_TIMEOUT);
        int acceptQueueSize = configuration.getInt(ACCEPT_QUEUE_SIZE_KEY, DEFAULT_ACCEPT_QUEUE_SIZE);
        String contextPath = configuration.getString(CONTEXT_PATH_KEY, DEFAULT_CONTEXT_PATH);
        String descriptor = configuration.getString(SERVER_DESCRIPTOR, DEFAULT_SERVER_DESCRIPTOR);
        String webappPath = configuration.getString(SERVER_WEBAPP_PATH, DEFAULT_SERVER_WEBAPP_PATH);

        serverConfig = JettyServerConfig.newBuilder()
                .acceptQueueSize(acceptQueueSize)
                .contextPath(contextPath)
                .descriptor(descriptor)
                .maxThreads(maxThreads)
                .minThreads(minThreads)
                .port(port)
                .timeout(timeout)
                .webappPath(webappPath)
                .build();
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        stop();
    }

    /**
     * 配置服务连接器
     * @return
     */
    private ServerConnector configServerConnector() {
        connector = new ServerConnector(server);
        connector.setPort(serverConfig.port());
        connector.setIdleTimeout(serverConfig.timeout());
        connector.setAcceptQueueSize(serverConfig.acceptQueueSize());
        if (StringUtils.isNotBlank(serverConfig.host())) {
            connector.setHost(serverConfig.host());
        }
        connector.setReuseAddress(true);

        return connector;
    }

    /**
     * 配置web上下文
     * @param scratchDir
     */
    private void configWebAppContext(File scratchDir) {

        webAppContext.setAttribute("javax.servlet.context.tempdir", scratchDir);

        webAppContext.setContextPath(serverConfig.contextPath());    //设置上下文根路径

        webAppContext.setAttribute("org.eclipse.jetty.containerInitializers", Arrays.asList (
                new ContainerInitializer(new JettyJasperInitializer(), null)));
        webAppContext.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        webAppContext.addBean(new ServletContainerInitializersStarter(webAppContext), true);

        setContextLoaderListener();
        FilterHolder filterHolder = createEncodingFilter();
        if (Objects.nonNull(filterHolder))
            webAppContext.addFilter(filterHolder, "/*", EnumSet.allOf(DispatcherType.class));  //添加编码过滤器，解决中文问题
        setDispatcher(); //引入Apache CXF、Jersey、Spring、ResetEasy等，提供Restful Web Service能力
        if (serverConfig.webappPath().contains(",")) {
            String webappPath = getWebappPath(serverConfig.webappPath().split(","));
            webAppContext.setDescriptor(webappPath.endsWith("/") ? webappPath + WEBXML : webappPath + "/" + WEBXML);
            webAppContext.setResourceBase(webappPath);
        } else {
            webAppContext.setDescriptor(serverConfig.descriptor());
            webAppContext.setResourceBase(serverConfig.webappPath());
        }

        webAppContext.setParentLoaderPriority(true);
    }

    /**
     * Establish Scratch directory for the servlet context (used by JSP compilation)
     */
    private File getScratchDir() throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }
        return scratchDir;
    }

    /**
     * Establish the Server URI
     */
    private URI getServerUri(ServerConnector connector) throws URISyntaxException {
        String scheme = "http";
        for (ConnectionFactory connectFactory : connector.getConnectionFactories()) {
            if (connectFactory.getProtocol().equals("SSL-http")) {
                scheme = "https";
            }
        }
        String host = connector.getHost();
        if (host == null) {
            host = "localhost";
        }
        int port = connector.getLocalPort();
        serverURI = new URI(String.format("%s://%s:%d/", scheme, host, port));
        Info.log(JettyBootstrap.class, "Server URI: " + serverURI);
        return serverURI;
    }

    private String getWebappPath(String[] webappPaths) {
        for (String webappPath : webappPaths) {
            if (new File(webappPath, WEBXML).exists()) {
                Info.log(this.getClass(), "webappPath: {}", webappPath);
                return webappPath;
            }
        }
        throw new BootstrapException("not find any webappPath");
    }

    protected void setDispatcher() {

    }

    protected FilterHolder createEncodingFilter() {
        return null;
    }

    /**
     * 默认使用spring
     */
    protected void setContextLoaderListener() {
        if (StringUtils.isNotBlank(this.applicationConfig)) {
            webAppContext.addEventListener(new CheetahContextLoaderListener());  //提供Spring支持能力
            webAppContext.setInitParameter("contextConfigLocation", applicationConfig);    //Spring配置文件位置
        }
    }

    public void addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        webAppContext.addServlet(servlet, pathSpec);
    }

    public void addServlet(ServletHolder servletHolder, String pathSpec) {
        webAppContext.addServlet(servletHolder, pathSpec);
    }

    public void addFilter(Class<? extends Filter> filterClass, String pathSpec) {
        webAppContext.addFilter(filterClass, pathSpec, null);
    }

    public void addFilter(FilterHolder filterHolder, String pathSpec) {
        webAppContext.addFilter(filterHolder, pathSpec, null);
    }

    public void addEventListener(EventListener eventListener) {
        webAppContext.addEventListener(eventListener);
    }

    private void stop() {
        if (null != server) {
            try {
                server.destroy();
                server.stop();
            } catch (Exception e) {
                Err.log(getClass(), "stop jetty server occurs error.", e);
            }
        }
    }


    /**
     * Cause server to keep running until it receives a Interrupt.
     * <p>
     * Interrupt Signal, or SIGINT (Unix Signal), is typically seen as a result of a kill -TERM {pid} or Ctrl+C
     *
     * @throws InterruptedException if interrupted
     */
    public void waitForInterrupt() throws InterruptedException {
        server.join();
    }
}
