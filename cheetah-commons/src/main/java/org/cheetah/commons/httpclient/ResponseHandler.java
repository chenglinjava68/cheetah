package org.cheetah.commons.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * Created by maxhuang on 2016/7/6.
 */
public interface ResponseHandler<T> {
    T handle(CloseableHttpResponse response);
}
