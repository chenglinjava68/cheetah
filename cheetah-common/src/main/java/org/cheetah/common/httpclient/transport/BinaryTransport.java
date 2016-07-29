package org.cheetah.common.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.common.httpclient.AbstractHttpTransport;
import org.cheetah.common.httpclient.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class BinaryTransport extends AbstractHttpTransport<byte[]> {
    private final Logger logger = LoggerFactory.getLogger(BinaryTransport.class);

    public BinaryTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected byte[] translate(HttpEntity entity) {
        try {
            byte[] result = EntityUtils.toByteArray(entity);
            logger.info("http request success, result type is bytes, length {}", result.length);
            return result;
        } catch (IOException e) {
            throw new HttpClientException("entity to bytes error", e);
        }
    }

}
