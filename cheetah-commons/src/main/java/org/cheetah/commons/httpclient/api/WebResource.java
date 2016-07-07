package org.cheetah.commons.httpclient.api;

import com.google.common.collect.ImmutableMap;
import org.apache.poi.ss.formula.functions.T;
import org.cheetah.commons.httpclient.ResourceSerializer;
import org.cheetah.commons.httpclient.serializer.Jackson2JsonSerializer;
import org.cheetah.commons.utils.Assert;
import org.cheetah.commons.utils.StringUtils;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class WebResource {
    private HttpClientFacade httpClientFacade;
    private ResourceSerializer serializer;
    private String resource;
    private String entity;
    private ImmutableMap<String, String> parameters = ImmutableMap.of();
    private ImmutableMap<String, String> headers = ImmutableMap.of();

    public WebResource(String resource) {
        this(resource, new Jackson2JsonSerializer(), HttpClientFacadeBuilder.defaultHttpClientFacade());
    }

    public WebResource(HttpClientFacade httpClientFacade, String resource) {
        this(resource, new Jackson2JsonSerializer(), httpClientFacade);
    }

    public WebResource(String resource, ResourceSerializer serializer, HttpClientFacade httpClientFacade) {
        this.resource = resource;
        this.httpClientFacade = httpClientFacade;
        this.serializer = serializer;
    }

    WebResource(String resource, String entity, ImmutableMap<String, String> headers, ImmutableMap<String, String> parameters, ResourceSerializer serializer) {
        this.resource = resource;
        this.entity = entity;
        this.headers = headers;
        this.parameters = parameters;
        this.serializer = serializer;
        this.httpClientFacade = HttpClientFacadeBuilder.defaultHttpClientFacade();
    }

    public WebResource type(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(headers).put(name, value).build();
        return new WebResource(this.resource, this.entity, newHeaders, this.parameters, serializer);
    }

    public WebResource parameter(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).put(name, value).build();
        return new WebResource(this.resource, this.entity, this.headers, newparameters, serializer);
    }

    public WebResource entity(String entity) {
        return new WebResource(this.resource, entity, this.headers, this.parameters, serializer);
    }

    public WebResource entity(Object entity) {
        String entityJson = this.serializer.serialize(entity);
        return new WebResource(this.resource, entityJson, this.headers, this.parameters, serializer);
    }

    public String post() {
        if (StringUtils.isNotBlank(entity))
            return httpClientFacade.post(this.resource, this.entity, this.headers);
        else {
            return httpClientFacade.post(this.resource, parameters, headers);
        }
    }

    public T post(Class<T> entity) {
        String result = post();
        return serializer.deserialize(result, entity);
    }

    public String get() {
        return httpClientFacade.get(this.resource, this.parameters, this.headers);
    }

    public T get(Class<T> entity) {
        String result = get();
        return serializer.deserialize(result, entity);
    }

    /**
     * 注意，这里使用的是POST
     *
     * @return
     */
    public byte[] load() {
        return httpClientFacade.load(this.parameters, this.headers, this.resource);
    }

    public byte[] getBinary() {
        return httpClientFacade.getBinary(this.resource, this.parameters, this.headers);
    }
}
