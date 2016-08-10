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
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * jetty服务封装
 *
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

    public static final int DEFAULT_MAX_THREADS = 256;
    public static final int DEFAULT_PORT = 8000;
    public static final int DEFAULT_ACCEPT_QUEUE_SIZE = 512;
    public static final int DEFAULT_IDLE_TIMEOUT = 30000;
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final String DEFAULT_SERVER_DESCRIPTOR = "WEB-INF/web.xml";
    public static final String DEFAULT_SERVER_WEBAPP_PATH = "webapp";

    private static final String DEFAULT_JETTY_CONFIG = "/jetty.properties";
    private static final String WEBXML = "WEB-INF/web.xml";
    /**
     * 读取配置库
     */
    private Configuration configuration;
    /**
     * 应用配置，Spring
     */
    private String applicationConfig;
    /**
     * 将服务配置转化为这个对象
     */
    protected JettyServerConfig serverConfig;
    /**
     * 配置文件路径
     */
    private String serverConfigPath;
    /**
     * jetty的server对象
     */
    private Server server;
    /**
     * jetty服务连接器
     */
    private ServerConnector connector;
    /**
     * webapp上下文
     */
    protected ServletContextHandler contextHandler;
    /**
     * servlet调度，如springmvc：DispatcherSerlvet
     */
    protected Class<? extends Servlet> dispatcher;
    /**
     * jetty服务启动后的uri
     */
    private URI serverURI;
    /**
     * jetty服务线程池， 默认使用QueuedThreadPool
     */
    private ThreadPool threadPool;
    /**
     * web模式，代表目前所启动的项目是一个web项目，包含web.xml和html(jsp)；
     * 当如果只是提供单纯的rest接口时，即仅是一个微服务，没有任何的页面也不使用web.xml，
     * 这种情况可以将其设置为false，这时需要手动通过api来添加servlet、filter和EventListener；
     * <p>
     * 默认值：false
     */
    private boolean webMode;

    /**
     * 配置源，1、true：加载环境变量；2、false：加载配置文件
     * 默认为 false， 加载配置文件
     */
    private boolean envConfigSource;

    public JettyBootstrap() {
        this(DEFAULT_JETTY_CONFIG);
    }

    public JettyBootstrap(String serverConfig) {
        this(serverConfig, null);
    }

    public JettyBootstrap(String serverConfig, String applicationConfig) {
        this(serverConfig, applicationConfig, null, null);
    }

    public JettyBootstrap(String applicationConfig, JettyServerConfig serverConfig, Class<? extends Servlet> dispatcher, ThreadPool threadPool) {
        this.applicationConfig = applicationConfig;
        this.serverConfig = serverConfig;
        this.dispatcher = dispatcher;
        this.threadPool = threadPool;
        initialize();
    }

    public JettyBootstrap(String serverConfig, String applicationConfig, Class<? extends Servlet> dispatcher, ThreadPool threadPool) {
        try {
            this.configuration = ConfigurationFactory.singleton().fromClasspath(serverConfig);
        } catch (Exception e) {
            throw new BootstrapException("read configuration local file error", e);
        }
        this.serverConfigPath = serverConfig;
        this.applicationConfig = applicationConfig;
        this.dispatcher = dispatcher;
        this.threadPool = threadPool;
        initialize();
    }

    @Override
    protected void startup() throws Exception {
        try {
            server = new Server(threadPool);

            ServerConnector connector = configureServerConnector();
            server.addConnector(connector);

            if (webMode) {
                contextHandler = new WebAppContext();
                configureWebAppContext(getScratchDir());
            } else {
                contextHandler = new ServletContextHandler();
                configureServletContextHandler();
            }

            server.setHandler(contextHandler);
            server.start();

            this.serverURI = getServerUri(connector);
        } catch (Exception e) {
            throw new BootstrapException("jetty boot occurs error.", e);
        }
    }

    /**
     * 初始化配置和上下文
     */
    private void initialize() {
        try {
            if (Objects.isNull(this.serverConfig))
                if (Objects.isNull(this.configuration) || envConfigSource)
                    loadEnvVariable();
                else
                    loadConfigFile();
        } catch (Exception e) {
            throw new BootstrapException("jetty config[" + serverConfigPath + "] read occurs error.", e);
        }

        if (this.threadPool == null)
            threadPool = new QueuedThreadPool(this.serverConfig.maxThreads(), this.serverConfig.minThreads(),
                    60000, new LinkedBlockingQueue<Runnable>());

        this.webMode = false;

        this.envConfigSource = false;
    }

    /**
     * 从环境变量中load出配置
     */
    private void loadEnvVariable() {
        int maxThreads = Integer.parseInt(System.getProperty(MAX_THREADS, DEFAULT_MAX_THREADS + ""));
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
        Info.log(this.getClass(), "jetty server configuration file from environment variable: {}", serverConfig.toString());
    }

    /**
     * 从配置文件中load配置
     */
    private void loadConfigFile() {
        int maxThreads = configuration.getInt(MAX_THREADS, DEFAULT_MAX_THREADS);
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
        Info.log(this.getClass(), "jetty server configuration file from a local file: {}", serverConfig.toString());
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        stop();
    }

    /**
     * 配置服务连接器
     *
     * @return
     */
    private ServerConnector configureServerConnector() {
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
     *
     * @param scratchDir
     */
    private void configureWebAppContext(File scratchDir) {
        WebAppContext context = (WebAppContext) this.contextHandler;

        configureServletContextHandler();
        context.setParentLoaderPriority(true);        //优先使用父类加载器

        String webappPath = getWebappPath(serverConfig.webappPath().split(","));
        if (serverConfig.webappPath().contains(","))
            context.setDescriptor(webappPath.endsWith("/") ? webappPath + WEBXML : webappPath + "/" + WEBXML);
        else
            context.setDescriptor(serverConfig.descriptor());
        contextHandler.setResourceBase(webappPath);

        context.setAttribute("javax.servlet.context.tempdir", scratchDir);
        //容器初始状态设置， 加入jsper初始化，解决jsp不是支持的问题
        context.setAttribute("org.eclipse.jetty.containerInitializers", Arrays.asList(
                new ContainerInitializer(new JettyJasperInitializer(), null)));
        context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        context.addBean(new ServletContainerInitializersStarter(context), true);
    }

    /**
     * 配置serlvet上下文
     */
    private void configureServletContextHandler() {
        this.contextHandler.setContextPath(serverConfig.contextPath());    //设置上下文根路径

        /**
         * load一个listener，通常是用来设置spring的监听器
         */
        setContextLoaderListener();
        /**
         * 字符编码过滤器
         */
        FilterHolder filterHolder = createEncodingFilter();
        if (Objects.nonNull(filterHolder))
            contextHandler.addFilter(filterHolder, "/*", EnumSet.allOf(DispatcherType.class));  //添加编码过滤器，解决中文问题
        setDispatcher(); //引入Apache CXF、Jersey、Spring、ResetEasy等，提供Restful Web Service能力
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
        throw new BootstrapException("not find available webappPath");
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
            contextHandler.addEventListener(new CheetahContextLoaderListener());  //提供Spring支持能力
            contextHandler.setInitParameter("contextConfigLocation", applicationConfig);    //Spring配置文件位置
        }
    }

    public void addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        contextHandler.addServlet(servlet, pathSpec);
    }

    public void addServlet(ServletHolder servletHolder, String pathSpec) {
        contextHandler.addServlet(servletHolder, pathSpec);
    }

    public void addFilter(Class<? extends Filter> filterClass, String pathSpec) {
        contextHandler.addFilter(filterClass, pathSpec, null);
    }

    public void addFilter(FilterHolder filterHolder, String pathSpec) {
        contextHandler.addFilter(filterHolder, pathSpec, null);
    }

    public void addEventListener(EventListener eventListener) {
        contextHandler.addEventListener(eventListener);
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

    public void setWebMode(boolean webMode) {
        this.webMode = webMode;
    }

    public void setEnvConfigSource(boolean envConfigSource) {
        this.envConfigSource = envConfigSource;
    }
}
