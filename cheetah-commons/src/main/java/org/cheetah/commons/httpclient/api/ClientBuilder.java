package org.cheetah.commons.httpclient.api;

import org.cheetah.commons.httpclient.Client;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.RestTransport;

import static org.cheetah.commons.httpclient.connector.ApacheHttpConnector.defaultApacheHttpConnector;

/**
 * Created by Max on 2015/11/26.
 */
public class ClientBuilder {
    BinaryTransport binaryTransport;
    RestTransport restTransport;

    public static ClientBuilder newBuilder() {
        return new ClientBuilder();
    }

    public Client build() {
        return new ClientImpl(binaryTransport, restTransport);
    }

    public static Client buildDefaultClient() {
        return ClientBuilder.newBuilder()
                .binaryTransport(new BinaryTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .restTransport(new RestTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .build();
    }

    public ClientBuilder binaryTransport(BinaryTransport binaryHttpTransport) {
        this.binaryTransport = binaryHttpTransport;
        return this;
    }

    public ClientBuilder restTransport(RestTransport restTransport) {
        this.restTransport = restTransport;
        return this;
    }

}
