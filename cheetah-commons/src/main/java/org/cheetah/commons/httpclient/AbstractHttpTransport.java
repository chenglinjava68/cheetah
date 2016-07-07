package org.cheetah.commons.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.commons.httpclient.transport.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Max on 2016/7/6.
 */
public abstract class AbstractHttpTransport<T> {
    private final Logger logger = LoggerFactory.getLogger(AbstractHttpTransport.class);

    private CloseableHttpClient httpClient;

    public AbstractHttpTransport(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public T doExecute(Transporter transporter, ResponseHandler<T> handler) {
        HttpRequestBase requestBase = null;
        CloseableHttpResponse resp = null;
        try {
            logger.info("request data : {}", transporter);

            switch (transporter.method()) {
                case POST:
                    requestBase = new HttpPost(transporter.url());
                    resp = executeBase((HttpPost) requestBase, transporter);
                    break;
                case PUT:
                    requestBase = new HttpPut(transporter.url());
                    resp = executeBase((HttpPut) requestBase, transporter);
                    break;
                case DELETE:
                    requestBase = new HttpDelete(transporter.url());
                    resp = executeBase(requestBase, transporter);
                    break;
                case HEAD:
                    requestBase = new HttpHead(transporter.url());
                    resp = executeBase(requestBase, transporter);
                    break;
                case TRACE:
                    requestBase = new HttpTrace(transporter.url());
                    resp = executeBase(requestBase, transporter);
                    break;
                case OPTIONS:
                    requestBase = new HttpOptions(transporter.url());
                    resp = executeBase(requestBase, transporter);
                    break;
                default:
                    requestBase = new HttpGet(transporter.url());
                    resp = executeBase(requestBase, transporter);
            }

            return handler.handle(resp);
        } catch (Exception e) {
            logger.error("The HTTP request an exception occurs, url :{}", transporter.url(), e);
            throw new HttpClientException("The HTTP request an exception occurs, url : " + transporter.url(), e);
        } finally {
            HttpClientUtils.close(requestBase, resp);
        }
    }

    private CloseableHttpResponse executeBase(HttpRequestBase requestBase, Transporter transporter) throws URISyntaxException, IOException {
        HttpClientUtils.setUriParameter(transporter.url(), transporter.parameters(), requestBase);
        HttpClientUtils.setHeader(transporter.headers(), requestBase);
        requestConfig(transporter, requestBase);
        return httpClient.execute(requestBase);
    }

    private CloseableHttpResponse executeBase(HttpEntityEnclosingRequestBase requestBase, Transporter transporter) throws IOException {
        HttpClientUtils.setFormParameter(transporter.parameters(), requestBase);
        HttpClientUtils.setBody(transporter.entity(), requestBase);
        HttpClientUtils.setHeader(transporter.headers(), requestBase);
        requestConfig(transporter, requestBase);
        return httpClient.execute(requestBase);
    }

    public void requestConfig(Transporter transporter, HttpRequestBase requestBase) {
        if(transporter.requestConfig() != null)
            requestBase.setConfig(transporter.requestConfig());
        else
            requestBase.setConfig(RequestConfig.DEFAULT);
    }

}
