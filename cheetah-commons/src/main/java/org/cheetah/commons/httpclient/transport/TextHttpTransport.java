package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.HttpGetException;
import org.cheetah.commons.httpclient.HttpPostException;
import org.cheetah.commons.httpclient.HttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Max on 2015/11/26.
 */
public abstract class TextHttpTransport implements HttpTransport<String> {
    private final Logger logger = LoggerFactory.getLogger(TextHttpTransport.class);

    @Override
    public String post(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) {
        String result = null;
        HttpPost post = null;
        CloseableHttpResponse resp = null;
        HttpEntity httpEntity = null;
        try {
            logger.info("request url : " + url);
            logger.info("request params : " + params);
            logger.info("request header : " + headers);
            post = new HttpPost(url);
            HttpClientUtils.setParameter(params, headers, post);
            resp = httpClient.execute(post);
            StatusLine statusLine = resp.getStatusLine();
            if (HttpClientUtils.resetSuccessStatus(statusLine)) {
                HttpClientUtils.gzipDecompression(resp);
                httpEntity = resp.getEntity();
                result = EntityUtils.toString(httpEntity, "UTF-8");
            }
            else throw new HttpPostException("Http get request error["+statusLine.getStatusCode()+"]-->url : " + url);
        } catch (Exception e) {
            logger.info("request error!", e);
            throw new HttpPostException("Http post request error-->url : " + url, e);
        } finally {
            HttpClientUtils.close(post, resp, httpEntity);
        }
        return result;
    }

    abstract String post(CloseableHttpClient httpClient, String url, String body, Map<String, String> headers);

    @Override
    public String get(CloseableHttpClient httpClient, String url, Map<String, String> params, Map<String, String> headers) {
        String result = null;
        HttpGet get = null;
        HttpEntity httpEntity = null;
        CloseableHttpResponse resp = null;
        try {
            logger.info("request url : " + url);
            logger.info("request params : " + params);
            logger.info("request header : " + headers);
            get = HttpClientUtils.setParameter(url, params, headers);
            resp = httpClient.execute(get);
            StatusLine statusLine = resp.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpClientUtils.gzipDecompression(resp);
                httpEntity = resp.getEntity();
                result = EntityUtils.toString(httpEntity, "UTF-8");
            } else
                throw new HttpGetException("Http get request error["+statusLine.getStatusCode()+"]-->url : " + url);
        } catch (Exception e) {
            logger.info("request error!", e);
            throw new HttpGetException("Http get request error-->url : " + url, e);
        } finally {
            HttpClientUtils.close(get, httpEntity, resp);
        }
        return result;
    }
}
