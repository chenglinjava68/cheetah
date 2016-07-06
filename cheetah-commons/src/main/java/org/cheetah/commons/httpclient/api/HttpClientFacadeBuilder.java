package org.cheetah.commons.httpclient.api;

import org.cheetah.commons.httpclient.connector.ApacheHttpConnector;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.RestTransport;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpClientFacadeBuilder {

    private final static ApacheHttpConnector apacheHttpConnector = new ApacheHttpConnector();

    BinaryTransport binaryHttpTransport;
    RestTransport restfulHttpTransport;

    public static HttpClientFacadeBuilder newBuilder() {
        return new HttpClientFacadeBuilder();
    }

    public HttpClientFacade build() {
        return new HttpClientFacade(this);
    }

    public static HttpClientFacade defaultHttpClientFacade() {
        return HttpClientFacadeBuilder.newBuilder()
                .binaryHttpTransport(new BinaryTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .restfulHttpTransport(new RestTransport(defaultApacheHttpConnector().getDefaultHttpClient()))
                .build();
    }

    public static ApacheHttpConnector defaultApacheHttpConnector() {
        return apacheHttpConnector;
    }

    public HttpClientFacadeBuilder binaryHttpTransport(BinaryTransport binaryHttpTransport) {
        this.binaryHttpTransport = binaryHttpTransport;
        return this;
    }

    public HttpClientFacadeBuilder restfulHttpTransport(RestTransport restfulHttpTransport) {
        this.restfulHttpTransport = restfulHttpTransport;
        return this;
    }


}
