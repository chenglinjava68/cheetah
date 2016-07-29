package org.cheetah.common.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.common.httpclient.AbstractHttpTransport;

/**
 * Created by Max on 2016/7/9.
 */
public class CustomTransport<T> extends AbstractHttpTransport<T> {

    public CustomTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected T translate(HttpEntity entity) {
        throw new UnsupportedOperationException();
    }
}
