package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.commons.httpclient.AbstractHttpTransport;

/**
 * Created by Max on 2016/7/9.
 */
public class CustomHttpTransport<T> extends AbstractHttpTransport<T> {

    public CustomHttpTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected T translate(HttpEntity entity) {
        throw new UnsupportedOperationException();
    }
}
