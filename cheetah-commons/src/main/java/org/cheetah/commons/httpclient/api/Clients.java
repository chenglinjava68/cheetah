package org.cheetah.commons.httpclient.api;


import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.commons.httpclient.Client;
import org.cheetah.commons.httpclient.connector.ApacheHttpConnector;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.StringTransport;

/**
 * Created by Max on 2016/7/6.
 */
public class Clients {
    public static WebResource resource(String url) {
        return new WebResource(url);
    }

    public static WebResource resource(String url, Client client) {
        return new WebResource(client, url);
    }

    public static Client getDefaultClient() {
        return ClientBuilder.buildDefaultClient();
    }

    public static Client getSafeClient(String keyPath, String password) {
        CloseableHttpClient httpClient = ApacheHttpConnector
                .defaultApacheHttpConnector()
                .createClientCertified(keyPath, password);
        return ClientBuilder.newBuilder()
                .binaryTransport(new BinaryTransport(httpClient))
                .stringTransport(new StringTransport(httpClient))
                .build();
    }

}
