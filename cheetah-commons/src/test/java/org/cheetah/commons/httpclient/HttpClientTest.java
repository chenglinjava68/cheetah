package org.cheetah.commons.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.api.HttpClientFacade;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {
    CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(new PoolingHttpClientConnectionManager(3, TimeUnit.SECONDS))
            .build();
    @Test
    public void test() {
        HttpClientFacade httpClient = new HttpClientFacade();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable run = () -> {
            while (true) {
                try {
                    httpClient.get("http://localhost:8080/test/on");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        while (true) {
            while (Thread.activeCount() < 10) {
                executorService.submit(run);
            }
        }
    }

    @Test
    public void test2() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable run = () -> {
            while (true) {
                try {
                    get(new URI("http://localhost:8080/test/on"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        };
        while (true) {
            while (Thread.activeCount() < 60) {
                executorService.submit(run);
            }
        }
    }

    protected String get(URI url) throws IOException {

        HttpClientFacade httpClient = new HttpClientFacade();
        HttpGet get = new HttpGet(url);
        get.setConfig(RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build());
        try {
            HttpResponse httpResponse = httpClient.getHttpclient().execute(get);
            if(httpResponse.getStatusLine().getStatusCode()!=200) {
                System.out.println("err");
            }
            return EntityUtils.toString(httpResponse.getEntity());
        } finally {
            get.releaseConnection();
        }
    }

}
