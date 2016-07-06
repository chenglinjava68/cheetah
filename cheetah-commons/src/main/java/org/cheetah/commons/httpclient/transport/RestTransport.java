package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.HttpPostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Max on 2015/11/26.
 */
public class RestTransport extends TextTransport {
    protected final Logger logger = LoggerFactory.getLogger(RestTransport.class);
    @Override
    String post(CloseableHttpClient httpClient, String url, String body, Map<String, String> headers) {
        String result = null;
        HttpPost post = null;
        CloseableHttpResponse resp = null;
        HttpEntity httpEntity = null;
        try {
            logger.info("request url : " + url );
            logger.info("request body : " + body);
            logger.info("request header : " + headers);
            post = new HttpPost(url);
            HttpClientUtils.setBody(body, headers, post);
            resp = httpClient.execute(post);
            StatusLine statusLine = resp.getStatusLine();

            if (HttpClientUtils.resetSuccessStatus(statusLine)) {
                logger.info("request ok!");
                HttpClientUtils.gzipDecompression(resp);
                httpEntity = resp.getEntity();
                result = EntityUtils.toString(httpEntity, "UTF-8");
            } else
                throw new HttpPostException("Http get request error["+statusLine.getStatusCode()+"]-->url : " + url);
        } catch (Exception e) {
            logger.info("request error!", e);
            throw new HttpPostException("Http post request error-->url : " + url, e);
        } finally {
            HttpClientUtils.close(post, resp, httpEntity);
        }
        return result;
    }
}
