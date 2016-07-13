package org.cheetah.commons.httpclient.connector;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.cheetah.commons.httpclient.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * httpclien工厂。
 *
 * @author Max
 * @version 1.0
 * @email max@tagsdata.com
 * @date 2014-12-11 下午5:11:59
 */
public class ApacheHttpConnector {
    private final Logger logger = LoggerFactory.getLogger(ApacheHttpConnector.class);

    private int maxConnPerHost = 500; // 设置 每个路由最大连接数
    private int maxTotalConn = 1000; // 设置最大连接数

    private final static ApacheHttpConnector apacheHttpConnector = new ApacheHttpConnector();
    private CloseableHttpClient defaultHttpclient = null;

    public ApacheHttpConnector() {
    }

    /**
     * 获取一个httpclient对象，该对象是唯一的，如果需要重新创建请调用createHttpClient方法
     *
     * @return
     */
    public CloseableHttpClient getDefaultHttpClient() {
        if (defaultHttpclient == null)
            defaultHttpclient = createHttpClient();
        return defaultHttpclient;
    }

    public static ApacheHttpConnector defaultApacheHttpConnector() {
        return apacheHttpConnector;
    }

    /**
     * 生成带证书httpclient
     *
     * @param keyPath  证书路径
     * @param password 证书密码
     * @return
     */
    public CloseableHttpClient createClientCertified(String keyPath,
                                                     String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(keyPath));
            try {
                keyStore.load(instream, password.toCharArray());
            } finally {
                instream.close();
            }

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, password.toCharArray()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            PoolingHttpClientConnectionManager clientConnectionManager = createConnectionManager(sslsf);

            return HttpClients.custom()
                    .setConnectionManager(clientConnectionManager)
                    .setRetryHandler(new RetryHandler())
                    .setSSLSocketFactory(sslsf).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientException("create httpclient error occured ", e);
        }
    }

    /**
     * 生成普通的httpclient
     * @return
     */
    public CloseableHttpClient createHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, (TrustStrategy) (chain, authType) -> true).build();
            PoolingHttpClientConnectionManager connectionManager = createConnectionManager(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return HttpClients.
                    custom()
                    .setConnectionManager(connectionManager)
                    .setRetryHandler(new RetryHandler()).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientException("create httpclient error occured ", e);
        }
    }

    /**
     * 创建连接管理器
     * @param sslContext
     * @param hostnameVerifier
     * @return
     */
    public PoolingHttpClientConnectionManager createConnectionManager(SSLContext sslContext, HostnameVerifier hostnameVerifier) {
        ConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> registry = registry(plainsf, sslsf);
        // init pool
        PoolingHttpClientConnectionManager connectionManager = doCreateConnectionManager(registry);

        connectionMonitor(connectionManager);

        return connectionManager;
    }
    /**
     * 创建连接管理器
     * @param sslConnectionSocketFactory
     * @return
     */
    public PoolingHttpClientConnectionManager createConnectionManager(SSLConnectionSocketFactory sslConnectionSocketFactory) {
        ConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();

        Registry<ConnectionSocketFactory> registry = registry(plainsf, sslConnectionSocketFactory);
        // init pool
        PoolingHttpClientConnectionManager connectionManager = doCreateConnectionManager(registry);

        connectionMonitor(connectionManager);

        return connectionManager;
    }

    private Registry<ConnectionSocketFactory> registry(ConnectionSocketFactory plainsf, SSLConnectionSocketFactory sslsf) {
        return RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", plainsf).register("https", sslsf).build();
    }

    private PoolingHttpClientConnectionManager doCreateConnectionManager(Registry<ConnectionSocketFactory> registry) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                registry);
        connectionManager.setMaxTotal(maxTotalConn);
        connectionManager.setDefaultMaxPerRoute(maxConnPerHost);
        return connectionManager;
    }

    private void connectionMonitor(PoolingHttpClientConnectionManager connectionManager) {
        IdleConnectionMonitorThread idleConnectionMonitor = new IdleConnectionMonitorThread(
                connectionManager);
        ScheduledExecutorService es = Executors
                .newSingleThreadScheduledExecutor();
        es.scheduleWithFixedDelay(idleConnectionMonitor, 5, 5,
                TimeUnit.SECONDS);

        logger.info("schedule status : " + es.isShutdown());
    }

    public void setMaxConnPerHost(int maxConnPerHost) {
        this.maxConnPerHost = maxConnPerHost;
    }

    public void setMaxTotalConn(int maxTotalConn) {
        this.maxTotalConn = maxTotalConn;
    }

    private class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                synchronized (this) {
                    logger.info("##############################################");
                    logger.info("#                  执行清理                   #");
                    logger.info("##############################################");
                    wait(5000); // 关闭失效的连接
                    connMgr.closeExpiredConnections(); // 可选的, 关闭30秒内不活动的连接
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
