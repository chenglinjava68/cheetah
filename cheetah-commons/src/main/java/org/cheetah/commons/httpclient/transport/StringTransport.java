package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.api.AbstractHttpTransport;
import org.cheetah.commons.httpclient.api.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class StringTransport extends AbstractHttpTransport<String> {
    private final Logger logger = LoggerFactory.getLogger(StringTransport.class);

    public StringTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected String translate(HttpEntity entity) {
        try {
            String result = EntityUtils.toString(entity);
            logger.info("http request success, content: \n{}", result);
            return result;
        } catch (IOException e) {
            throw new HttpClientException("entity to string error", e);
        }
    }

}
