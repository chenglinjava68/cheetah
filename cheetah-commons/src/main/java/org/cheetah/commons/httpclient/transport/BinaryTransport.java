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
public class BinaryTransport extends AbstractHttpTransport<byte[]> implements HttpTransport<byte[]> {
    private final Logger logger = LoggerFactory.getLogger(BinaryTransport.class);

    public BinaryTransport(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public byte[] execute(Transporter transporter) {
        return doExecute(transporter, entity -> {
            try {
                byte[] result =  EntityUtils.toByteArray(entity);
                logger.info("result byte length : {}", result.length);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpClientException("Http post request error-->url : " + transporter.url(), e);
            }
        });
    }
}
