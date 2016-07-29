package org.cheetah.common.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.common.httpclient.transport.HttpClientUtils;
import org.cheetah.common.httpclient.transport.ResponseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * http运输抽象类
 * 实现方法选择，参数设置，头部信息设置，数据解压，http请求执行
 *
 * Created by Max on 2016/7/6.
 */
public abstract class AbstractHttpTransport<T> implements HttpTransport<T> {
    private final Logger logger = LoggerFactory.getLogger(AbstractHttpTransport.class);

    private CloseableHttpClient httpClient;

    public AbstractHttpTransport(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     *
     * @param entity
     * @return
     */
    protected abstract T translate(HttpEntity entity);

    /**
     *
     * @param requester 请求者
     * @return
     */
    @Override
    public T execute(Requester requester) {
        return doExecute(requester, response -> {
            HttpEntity httpEntity = response.getEntity();
            ResponseProcessor<T> processor = entity -> translate(httpEntity);
            return processor.process(response);
        });
    }

    @Override
    public T execute(Requester requester, ResponseHandler<T> handler) {
        return doExecute(requester, handler);
    }

    /**
     *
     * @param requester
     * @param handler
     * @return
     */
    private T doExecute(Requester requester, ResponseHandler<T> handler) {
        HttpRequestBase requestBase = null;
        CloseableHttpResponse resp = null;
        try {
            logger.info("request data : {}", requester);

            switch (requester.method()) {
                case POST:
                    requestBase = new HttpPost(requester.url());
                    resp = executeBase((HttpPost) requestBase, requester);
                    break;
                case PATCH:
                    requestBase = new HttpPatch(requester.url());
                    resp = executeBase((HttpPatch) requestBase, requester);
                    break;
                case PUT:
                    requestBase = new HttpPut(requester.url());
                    resp = executeBase((HttpPut) requestBase, requester);
                    break;
                case DELETE:
                    requestBase = new HttpDelete(requester.url());
                    resp = executeBase(requestBase, requester);
                    break;
                case HEAD:
                    requestBase = new HttpHead(requester.url());
                    resp = executeBase(requestBase, requester);
                    break;
                case TRACE:
                    requestBase = new HttpTrace(requester.url());
                    resp = executeBase(requestBase, requester);
                    break;
                case OPTIONS:
                    requestBase = new HttpOptions(requester.url());
                    resp = executeBase(requestBase, requester);
                    break;
                default:
                    requestBase = new HttpGet(requester.url());
                    resp = executeBase(requestBase, requester);
            }

            return handler.handle(resp);
        } catch (Exception e) {
            logger.error("The HTTP request an exception occurs, url :{}", requester.url(), e);
            throw new HttpClientException("The HTTP request an exception occurs, url : " + requester.url(), e);
        } finally {
            HttpClientUtils.close(requestBase, resp);
        }
    }

    /**
     *
     * @param requestBase
     * @param requester
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private CloseableHttpResponse executeBase(HttpRequestBase requestBase, Requester requester) throws URISyntaxException, IOException {
        HttpClientUtils.setUriParameter(requester.url(), requester.parameters(), requestBase);
        HttpClientUtils.setHeader(requester.headers(), requestBase);
        requestConfig(requester, requestBase);
        return httpClient.execute(requestBase);
    }

    /**
     *
     * @param requestBase
     * @param requester
     * @return
     * @throws IOException
     */
    private CloseableHttpResponse executeBase(HttpEntityEnclosingRequestBase requestBase, Requester requester) throws IOException {
        HttpClientUtils.setFormParameter(requester.parameters(), requestBase);
        HttpClientUtils.setBody(requester.entity(), requestBase);
        HttpClientUtils.setHeader(requester.headers(), requestBase);
        requestConfig(requester, requestBase);
        return httpClient.execute(requestBase);
    }

    /**
     *
     * @param requester
     * @param requestBase
     */
    private void requestConfig(Requester requester, HttpRequestBase requestBase) {
        if (requester.requestConfig() != null)
            requestBase.setConfig(requester.requestConfig());
        else
            requestBase.setConfig(RequestConfig.DEFAULT);
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
