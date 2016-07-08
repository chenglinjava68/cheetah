package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class RestTransport extends AbstractHttpTransport<String> {
    private final Logger logger = LoggerFactory.getLogger(RestTransport.class);

    public RestTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected String translate(HttpEntity entity) {
        try {
            String entityJson = EntityUtils.toString(entity);
            logger.info("http request success, content: \n{}", entityJson);
            return entityJson;
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpClientException("entity to string error", e);
        }
    }

}
