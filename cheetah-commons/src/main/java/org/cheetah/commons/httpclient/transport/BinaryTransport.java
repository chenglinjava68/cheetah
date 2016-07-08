package org.cheetah.commons.httpclient.transport;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.AbstractHttpTransport;
import org.cheetah.commons.httpclient.HttpClientException;
import org.cheetah.commons.httpclient.HttpTransport;
import org.cheetah.commons.httpclient.Requester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class BinaryTransport extends AbstractHttpTransport<byte[]> implements HttpTransport<byte[]> {
    private final Logger logger = LoggerFactory.getLogger(BinaryTransport.class);
    private ResponseProcessor<byte[]> processor;

    public BinaryTransport(CloseableHttpClient httpClient) {
        super(httpClient);
        initProcessor();
    }

    @Override
    public byte[] execute(Requester requester) {
        return doExecute(requester, response -> processor.process(response));
    }

    void initProcessor() {
        this.processor = entity -> {
            try {
                byte[] result = EntityUtils.toByteArray(entity);
                logger.info("http request success, result type is bytes, length {}", result.length);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpClientException("entity to bytes error", e);
            }
        };
    }
}
