package org.cheetah.commons.httpclient.transport;

import org.apache.http.impl.client.CloseableHttpClient;
import org.cheetah.commons.httpclient.ChunkTransport;
import org.cheetah.commons.httpclient.connector.ApacheHttpConnector;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpClientFacadeBuilder {

    private final static ApacheHttpConnector apacheHttpConnector = new ApacheHttpConnector();

    CloseableHttpClient httpclient;
    BinaryTransport binaryHttpTransport;
    RestTransport restfulHttpTransport;
    ChunkTransport chunkHttpTransport;

    public static HttpClientFacadeBuilder newBuilder() {
        return new HttpClientFacadeBuilder();
    }

    public HttpClientFacade build() {
        return new HttpClientFacade(this);
    }

    public static HttpClientFacade defaultClients() {
        return HttpClientFacadeBuilder.newBuilder()
                .binaryHttpTransport(new BinaryTransport())
                .chunkHttpTransport(new FileTransport())
                .httpclient(defaultApacheHttpConnector().getDefaultHttpClient())
                .restfulHttpTransport(new RestTransport())
                .build();
    }

    public static ApacheHttpConnector defaultApacheHttpConnector() {
        return apacheHttpConnector;
    }

    public HttpClientFacadeBuilder httpclient(CloseableHttpClient httpclient) {
        this.httpclient = httpclient;
        return this;
    }

    public HttpClientFacadeBuilder binaryHttpTransport(BinaryTransport binaryHttpTransport) {
        this.binaryHttpTransport = binaryHttpTransport;
        return this;
    }

    public HttpClientFacadeBuilder restfulHttpTransport(RestTransport restfulHttpTransport) {
        this.restfulHttpTransport = restfulHttpTransport;
        return this;
    }

    public HttpClientFacadeBuilder chunkHttpTransport(ChunkTransport chunkHttpTransport) {
        this.chunkHttpTransport = chunkHttpTransport;
        return this;
    }
}
