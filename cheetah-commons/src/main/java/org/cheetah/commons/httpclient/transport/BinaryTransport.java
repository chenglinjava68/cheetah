package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.AbstractHttpTransport;
import org.cheetah.commons.httpclient.HttpClientException;
import org.cheetah.commons.httpclient.HttpTransport;
import org.cheetah.commons.httpclient.Requester;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class BinaryTransport extends AbstractHttpTransport<byte[]> implements HttpTransport<byte[]> {

    public BinaryTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public byte[] execute(Requester requester) {
        return doExecute(requester, response -> {
            ResponseProcessor<byte[]> processor = new ResponseProcessor<byte[]>() {
                @Override
                public void onFailure(StatusLine statusLine) {
                    logger.error("request 1 failed with a {} response url : {}", statusLine.getStatusCode(), requester.url());
                    throw new HttpClientException("request 1 failed with a response");
                }

                @Override
                public byte[] onSuccess(HttpEntity entity) {
                    try {
                        byte[] result = EntityUtils.toByteArray(entity);
                        logger.info("http request success, result type is bytes, length {}", result.length);
                        return result;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new HttpClientException("entity to bytes error", e);
                    }
                }
            };

            return processor.process(response);
        });
    }
}
