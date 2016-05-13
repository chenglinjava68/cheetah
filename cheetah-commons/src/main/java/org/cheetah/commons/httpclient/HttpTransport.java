package org.cheetah.commons.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Max on 2015/11/26.
 */
public interface HttpTransport<T extends Serializable> {

    T post(CloseableHttpClient httpClient, String url,
           Map<String, String> params, Map<String, String> headers);

    T get(CloseableHttpClient httpClient, String url,
          Map<String, String> params, Map<String, String> headers);


}
