package org.cheetah.commons.httpclient.api;

import com.google.common.collect.ImmutableMap;
import org.apache.http.client.config.RequestConfig;
import org.apache.poi.ss.formula.functions.T;
import org.cheetah.commons.httpclient.ResourceSerializer;
import org.cheetah.commons.httpclient.Transporter;
import org.cheetah.commons.httpclient.serializer.Jackson2JsonSerializer;
import org.cheetah.commons.utils.Assert;

/**
 * 注意事项：post发出post请求时entity和parameters不能同时作为数据传输给服务方
 * 当两个同时存在的时候，会优先使用entity而抛弃parameters
 * Created by maxhuang on 2016/7/5.
 */
public class WebResource {
    public static final int GENERIC_TIMEOUT = 2000;

    private HttpClientFacade httpClientFacade;
    private ResourceSerializer serializer;
    private String resource;
    private String entity;
    private ImmutableMap<String, String> parameters = ImmutableMap.of();
    private ImmutableMap<String, String> headers = ImmutableMap.of();
    private int timeout = -1;

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

    WebResource(String resource, String entity, ImmutableMap<String, String> headers, ImmutableMap<String, String> parameters, ResourceSerializer serializer, int timeout) {
        this.resource = resource;
        this.entity = entity;
        this.headers = headers;
        this.parameters = parameters;
        this.serializer = serializer;
        this.timeout = timeout;
        this.httpClientFacade = HttpClientFacadeBuilder.defaultHttpClientFacade();
    }

    public WebResource type(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(headers).put(name, value).build();
        return new WebResource(this.resource, this.entity, newHeaders, this.parameters, serializer, this.timeout);
    }

    public WebResource parameter(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).put(name, value).build();
        return new WebResource(this.resource, this.entity, this.headers, newparameters, serializer, this.timeout);
    }

    public WebResource entity(String entity) {
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(HttpClientFacade.RESET_TYPE).putAll(headers).build();
        return new WebResource(this.resource, entity, newHeaders, this.parameters, serializer, this.timeout);
    }

    public WebResource entity(Object entity) {
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(HttpClientFacade.RESET_TYPE).putAll(headers).build();
        String entityJson = this.serializer.serialize(entity);
        return new WebResource(this.resource, entityJson, newHeaders, this.parameters, serializer, this.timeout);
    }

    public WebResource timeout(int timeout) {
        return new WebResource(this.resource, this.entity, this.headers, this.parameters, serializer, timeout);
    }

    public String post() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(GENERIC_TIMEOUT).build();
        return httpClientFacade.getRestfulTransport().execute(Transporter.POST()
                .url(this.resource)
                .entity(this.entity)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
    }

    public T post(Class<T> entity) {
        String result = post();
        return serializer.deserialize(result, entity);
    }

    public String get() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom()
                        .setConnectionRequestTimeout(GENERIC_TIMEOUT)
                        .setConnectTimeout(GENERIC_TIMEOUT)
                        .setSocketTimeout(timeout)
                        .build();
        return httpClientFacade.getRestfulTransport().execute(Transporter.GET()
                .url(this.resource)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
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

    public byte[] download() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom()
                        .setConnectionRequestTimeout(GENERIC_TIMEOUT)
                        .setConnectTimeout(GENERIC_TIMEOUT)
                        .setSocketTimeout(timeout)
                        .build();
        return httpClientFacade.getBinaryTransport().execute(Transporter.GET()
                .url(this.resource)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build());
    }
}
