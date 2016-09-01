package org.cheetah.commons.httpclient.api;

import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.StringTransport;

import static org.cheetah.commons.httpclient.connector.ApacheHttpConnector.defaultApacheHttpConnector;

/**
 * Created by Max on 2015/11/26.
 */
public class ClientBuilder {
    private BinaryTransport binaryTransport;
    private StringTransport stringTransport;

    public static ClientBuilder newBuilder() {
        return new ClientBuilder();
    }

    public Client build() {
        return new ClientImpl(binaryTransport, stringTransport);
    }

    public static Client buildDefaultClient() {
        return ClientBuilder.newBuilder()
                .binaryTransport(new BinaryTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .stringTransport(new StringTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .build();
    }

    public ClientBuilder binaryTransport(BinaryTransport binaryHttpTransport) {
        this.binaryTransport = binaryHttpTransport;
        return this;
    }

    public ClientBuilder stringTransport(StringTransport restTransport) {
        this.stringTransport = restTransport;
        return this;
    }

}
