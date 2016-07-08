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
public class RestTransport extends AbstractHttpTransport<String> implements HttpTransport<String> {
    private final Logger logger = LoggerFactory.getLogger(RestTransport.class);
    private ResponseProcessor<String> processor;

    public RestTransport(CloseableHttpClient httpClient) {
        super(httpClient);
        initProcessor();
    }

    @Override
    public String execute(Requester requester) {
        return doExecute(requester, response -> processor.process(response));
    }

    void initProcessor() {
        this.processor = entity -> {
            try {
                String entityJson = EntityUtils.toString(entity);
                logger.info("http request success, content: \n{}", entityJson);
                return entityJson;
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpClientException("entity to string error", e);
            }
        };
    }

}
