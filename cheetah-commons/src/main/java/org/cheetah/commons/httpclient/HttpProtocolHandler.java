package org.cheetah.commons.httpclient;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * httpclien工具类。
 *
 * @author Max
 * @version 1.0
 * @email max@tagsdata.com
 * @date 2014-12-11 下午5:11:59
 */
public class HttpProtocolHandler {
    private final Logger logger = LoggerFactory.getLogger(HttpProtocolHandler.class);

    private int maxConnPerHost = 50; // 设置 每个路由最大连接数
    private int maxTotalConn = 300; // 设置最大连接数
    // private static final int CONNECTION_TIMEOUT = 2 * 1000; //设置请求超时2秒钟
    // 根据业务调整
    // private static final int SO_TIMEOUT = 2 * 1000; //设置等待数据超时时间2秒钟 根据业务调整

    private CloseableHttpClient defaultHttpclient = null;

    private CloseableHttpClient weixinPayHttpClient = null;
    private CloseableHttpClient alipayHttpClient = null;

    public HttpProtocolHandler() {
    }

    /**
     * 获取一个httpclient对象，该对象是唯一的，如果需要重新创建请调用createHttpclientByPool方法
     *
     * @return
     */
    public CloseableHttpClient getOrdinaryHttpClient() {
        if (defaultHttpclient == null)
            defaultHttpclient = createOrdinaryHttpClient();
        return defaultHttpclient;
    }

    public CloseableHttpClient getWeixinHttpClient() {
        if (weixinPayHttpClient == null)
            weixinPayHttpClient = createWeixinPayClient("", "");
        return weixinPayHttpClient;
    }

    /**
     * 获取未支付httpclient
     *
     * @param keyPath  证书路径
     * @param password 证书密码
     * @return
     */
    public CloseableHttpClient createWeixinPayClient(String keyPath,
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
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(maxTotalConn);
            cm.setDefaultMaxPerRoute(maxConnPerHost);
            // 将目标主机的最大连接数增加到50
            // HttpHost localhost = new HttpHost("localhost", 80);
            // cm.setMaxPerRoute(new HttpRoute(localhost), 50);

            CloseableHttpClient httpclient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setRetryHandler(new RetryHandler())
                    .setSSLSocketFactory(sslsf).build();

            IdleConnectionMonitorThread idleConnectionMonitor = new IdleConnectionMonitorThread(
                    cm);
            ScheduledExecutorService es = Executors
                    .newSingleThreadScheduledExecutor();
            es.scheduleWithFixedDelay(idleConnectionMonitor, 5, 5,
                    TimeUnit.SECONDS);
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CloseableHttpClient createOrdinaryHttpClient() {
        CloseableHttpClient httpclient = null;
        SSLContext sslContext = null;
        try {
            ConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();

            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            sslContext = SSLContexts.custom().useTLS()
                    .loadTrustMaterial(trustStore, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", plainsf).register("https", sslsf).build();
            // init pool
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                    registry);
            cm.setMaxTotal(maxTotalConn);
            cm.setDefaultMaxPerRoute(maxConnPerHost);

            httpclient = HttpClients.custom().setConnectionManager(cm)
                    .setRetryHandler(new RetryHandler()).build();

            IdleConnectionMonitorThread idleConnectionMonitor = new IdleConnectionMonitorThread(
                    cm);
            ScheduledExecutorService es = Executors
                    .newSingleThreadScheduledExecutor();
            es.scheduleWithFixedDelay(idleConnectionMonitor, 5, 5,
                    TimeUnit.SECONDS);

            logger.info("schedule status : " + es.isShutdown());
            // idleConnectionMonitor.start();
            //
            // idleConnectionMonitor.shutdown();
            // idleConnectionMonitor.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpclient;
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
