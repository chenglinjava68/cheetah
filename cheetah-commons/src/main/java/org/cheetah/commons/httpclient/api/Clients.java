package org.cheetah.commons.httpclient.api;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by Max on 2016/7/6.
 */
public class Clients {
    public static WebResource resource(String url) {
        return new WebResource(url);
    }

    public static HttpClientFacade getDefaultHttpClientFacade() {
        return HttpClientFacadeBuilder.defaultHttpClientFacade();
    }

    public static CloseableHttpClient getDefaultHttpClient() {
        return HttpClientFacadeBuilder.defaultApacheHttpConnector().getDefaultHttpClient();
    }
}
