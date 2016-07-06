package org.cheetah.commons.httpclient.api;

import com.google.common.collect.ImmutableMap;
import org.cheetah.commons.utils.Assert;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class WebResource {
    private HttpClientFacade httpClientFacade = HttpClientFacadeBuilder.defaultHttpClientFacade();
    private String resource;
    private String body;
    private ImmutableMap<String, String> parameters = ImmutableMap.of();
    private ImmutableMap<String, String> headers = ImmutableMap.of();

    public WebResource(String resource) {
        this.resource = resource;
    }

    public WebResource(HttpClientFacade httpClientFacade, String resource) {
        this.httpClientFacade = httpClientFacade;
        this.resource = resource;
    }

    WebResource(String resource, String body, ImmutableMap<String, String> headers, ImmutableMap<String, String> parameters) {
        this.resource = resource;
        this.body = body;
        this.headers = headers;
        this.parameters = parameters;
    }

    public WebResource type(String key, String value) {
        Assert.notBlank(key, "key must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(headers).put(key, value).build();
        return new WebResource(this.resource, this.body, newHeaders, this.parameters);
    }

    public WebResource parameter(String key, String value) {
        Assert.notBlank(key, "key must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).put(key, value).build();
        return new WebResource(this.resource, this.body, this.headers, newparameters);
    }

    public WebResource body(String body) {
        return new WebResource(this.resource, body, this.headers, this.parameters);
    }

    public String post() {
        return  httpClientFacade.post(this.resource, this.parameters, this.headers);
    }

    public String get() {
       return  httpClientFacade.get(this.resource, this.parameters, this.headers);
    }

    /**
     * 注意，这里使用的是POST
     * @return
     */
    public byte[] load() {
        return  httpClientFacade.post(this.parameters, this.headers, this.resource);
    }

    public byte[] getBinary() {
        return  httpClientFacade.getBinary(this.resource, this.parameters, this.headers);
    }
}
