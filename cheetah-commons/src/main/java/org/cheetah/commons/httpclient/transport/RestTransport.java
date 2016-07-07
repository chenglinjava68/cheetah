package org.cheetah.commons.httpclient.transport;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.cheetah.commons.httpclient.AbstractHttpTransport;
import org.cheetah.commons.httpclient.HttpClientException;
import org.cheetah.commons.httpclient.HttpTransport;
import org.cheetah.commons.httpclient.Transporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Max on 2015/11/26.
 */
public class RestTransport extends AbstractHttpTransport<String> implements HttpTransport<String> {

    protected final Logger logger = LoggerFactory.getLogger(RestTransport.class);

    public RestTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public String execute(Transporter transporter) {
        return doExecute(transporter, response -> {
            ResponseProcessor<String> processor = new ResponseProcessor<String>() {
                @Override
                public void onFailure(StatusLine statusLine) {
                    logger.error("request 1 failed with a {} response url : {}", statusLine.getStatusCode(), transporter.url());
                    throw new HttpClientException("request 1 failed with a response");
                }

                @Override
                public String onSuccess(HttpEntity entity) {
                    try {
                        String entityJson = EntityUtils.toString(entity);
                        logger.info("http request success, content: \n{}", entityJson);
                        return entityJson;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new HttpClientException("entity to string error", e);
                    }
                }
            };

            return processor.process(response);
        });
    }

}
