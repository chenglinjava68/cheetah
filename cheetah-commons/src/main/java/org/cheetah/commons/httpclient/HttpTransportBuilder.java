package org.cheetah.commons.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpTransportBuilder {
    private HttpProtocolHandler httpProtocolHandler;
    private CloseableHttpClient httpclient;
    private BinaryHttpTransport binaryHttpTransport;
    private RestfulHttpTransport restfulHttpTransport;
    private ChunkHttpTransport chunkHttpTransport;

    public static HttpTransportBuilder newBuilder() {
        return new HttpTransportBuilder();
    }


    public CloseableHttpClient buildhttpclient() {
        this.httpProtocolHandler = new HttpProtocolHandler();
        this.httpclient = httpProtocolHandler.createOrdinaryHttpClient();
        return httpclient;
    }

    public BinaryHttpTransport buildBinaryHttpTransport() {
        this.binaryHttpTransport = new BinaryHttpTransport();
        return binaryHttpTransport;
    }

    public RestfulHttpTransport buildRestfulHttpTransport() {
        this.restfulHttpTransport = new RestfulHttpTransport();
        return restfulHttpTransport;
    }

    public ChunkHttpTransport buildChunkHttpTransport() {
        this.chunkHttpTransport = new FileHttpTransport();
        return chunkHttpTransport;
    }
}
