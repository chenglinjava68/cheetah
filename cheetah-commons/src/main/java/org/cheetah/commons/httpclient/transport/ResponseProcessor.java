package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.cheetah.commons.httpclient.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maxhuang on 2016/7/7.
 */
public interface ResponseProcessor<T> {
    Logger logger = LoggerFactory.getLogger(ResponseProcessor.class);

    default T process(CloseableHttpResponse response) {
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            if (HttpClientUtils.resetSuccessStatus(statusLine)) {
                logger.info("everything is ok!");
                HttpClientUtils.gzipDecompression(response);
                logger.info("result entity : {}", entity);
                return onSuccess(entity);
            } else
                onFailure(statusLine);
            return null;
        } finally {
            HttpClientUtils.close(response, entity);
        }
    }

    default void onFailure(StatusLine statusLine) {
        throw new HttpClientException("Http post request error, status code " + statusLine.getStatusCode());
    }

    default T onSuccess(HttpEntity entity) {
        return null;
    }
}
