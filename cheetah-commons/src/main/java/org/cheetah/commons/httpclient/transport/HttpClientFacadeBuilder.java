package org.cheetah.commons.httpclient.transport;

import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.commons.httpclient.ChunkHttpTransport;
import org.cheetah.commons.httpclient.connector.ApacheHttpConnector;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpClientFacadeBuilder {

    private final static ApacheHttpConnector apacheHttpConnector = new ApacheHttpConnector();

    CloseableHttpClient httpclient;
    BinaryHttpTransport binaryHttpTransport;
    RestfulHttpTransport restfulHttpTransport;
    ChunkHttpTransport chunkHttpTransport;

    public static HttpClientFacadeBuilder newBuilder() {
        return new HttpClientFacadeBuilder();
    }

    public HttpClientFacade build() {
        return new HttpClientFacade(this);
    }

    public static HttpClientFacade defaultClients() {
        return HttpClientFacadeBuilder.newBuilder()
                .binaryHttpTransport(new BinaryHttpTransport())
                .chunkHttpTransport(new FileHttpTransport())
                .httpclient(defaultApacheHttpConnector().getDefaultHttpClient())
                .restfulHttpTransport(new RestfulHttpTransport())
                .build();
    }

    public static ApacheHttpConnector defaultApacheHttpConnector() {
        return apacheHttpConnector;
    }

    public HttpClientFacadeBuilder httpclient(CloseableHttpClient httpclient) {
        this.httpclient = httpclient;
        return this;
    }

    public HttpClientFacadeBuilder binaryHttpTransport(BinaryHttpTransport binaryHttpTransport) {
        this.binaryHttpTransport = binaryHttpTransport;
        return this;
    }

    public HttpClientFacadeBuilder restfulHttpTransport(RestfulHttpTransport restfulHttpTransport) {
        this.restfulHttpTransport = restfulHttpTransport;
        return this;
    }

    public HttpClientFacadeBuilder chunkHttpTransport(ChunkHttpTransport chunkHttpTransport) {
        this.chunkHttpTransport = chunkHttpTransport;
        return this;
    }
}
