package org.cheetah.commons.httpclient.transport;

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
        return doExecute(transporter, entity -> {
            try {
                String resultEntity =  EntityUtils.toString(entity);
                logger.info("result entity : {}", resultEntity);
                return resultEntity;
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpClientException("Http post request error-->url : " + transporter.url(), e);
            }
        });
    }
}
