package org.cheetah.commons.httpclient.api;

import com.google.common.collect.ImmutableMap;
import org.apache.http.client.config.RequestConfig;
import org.cheetah.commons.httpclient.Client;
import org.cheetah.commons.httpclient.EntitySerializer;
import org.cheetah.commons.httpclient.Form;
import org.cheetah.commons.httpclient.Requester;
import org.cheetah.commons.httpclient.serializer.Jackson2JsonSerializer;
import org.cheetah.commons.utils.Assert;

import java.util.Map;

/**
 * 一次请求产生一个资源
 *
 * 模型数据为http请求所需
 * client http客户端
 * serializer 为entity数据序列化器，目前只实现了jackson的json序列化
 *
 * 注意事项：发出post请求时entity和parameters不能同时作为数据传输给服务方
 * 当两个同时存在的时候，会优先使用entity而抛弃parameters,如果想同时使用两者可以自己在外部
 * 拼接url参数
 * Created by maxhuang on 2016/7/5.
 */
public class WebResource {
    public static final int GENERIC_TIMEOUT = 2000;

    private Client client;
    private EntitySerializer serializer;
    private String resource;
    private String entity;
    private ImmutableMap<String, String> parameters = ImmutableMap.of();
    private ImmutableMap<String, String> headers = ImmutableMap.of();
    private int timeout = -1;

    public WebResource(String resource) {
        this(resource, new Jackson2JsonSerializer(), ClientBuilder.buildDefaultClient());
    }

    public WebResource(Client client, String resource) {
        this(resource, new Jackson2JsonSerializer(), client);
    }

    public WebResource(String resource, EntitySerializer serializer, Client client) {
        this.resource = resource;
        this.client = client;
        this.serializer = serializer;
    }

    WebResource(String resource, String entity, ImmutableMap<String, String> headers, ImmutableMap<String, String> parameters, EntitySerializer serializer, int timeout, Client client) {
        this.resource = resource;
        this.entity = entity;
        this.headers = headers;
        this.parameters = parameters;
        this.serializer = serializer;
        this.timeout = timeout;
        this.client = client;
    }

    public WebResource type(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(headers).put(name, value).build();
        return new WebResource(this.resource, this.entity, newHeaders, this.parameters, serializer, this.timeout, this.client);
    }

    public WebResource parameter(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).put(name, value).build();
        return new WebResource(this.resource, this.entity, this.headers, newparameters, serializer, this.timeout, this.client);
    }

    public WebResource form(Form form) {
        Assert.notNull(form, "form must not be null");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).putAll(form.parameters()).build();
        return new WebResource(this.resource, this.entity, this.headers, newparameters, serializer, this.timeout, this.client);
    }

    public WebResource parameters(Map<String, String> $parameters) {
        Assert.notNull($parameters, "$parameters must not be null");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(this.parameters).putAll($parameters).build();
        return new WebResource(this.resource, this.entity, this.headers, newparameters, serializer, this.timeout, this.client);
    }

    public WebResource entity(String entity) {
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(Client.RESET_TYPE).putAll(headers).build();
        return new WebResource(this.resource, entity, newHeaders, this.parameters, serializer, this.timeout, this.client);
    }

    public WebResource entity(Object entity) {
        ImmutableMap<String, String> newHeaders = ImmutableMap.<String, String>builder().putAll(Client.RESET_TYPE).putAll(headers).build();
        String entityJson = this.serializer.serialize(entity);
        return new WebResource(this.resource, entityJson, newHeaders, this.parameters, serializer, this.timeout, this.client);
    }

    public WebResource timeout(int timeout) {
        return new WebResource(this.resource, this.entity, this.headers, this.parameters, serializer, timeout, this.client);
    }

    public String post() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom().setConnectTimeout(GENERIC_TIMEOUT).setSocketTimeout(timeout).build();
        return client.getStringTransport().execute(Requester.POST()
                .url(this.resource)
                .entity(this.entity)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
    }

    public <T> T post(Class<T> entity) {
        String result = post();
        return serializer.deserialize(result, entity);
    }

    public String put() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom().setConnectTimeout(GENERIC_TIMEOUT).setSocketTimeout(timeout).build();
        return client.getStringTransport().execute(Requester.PUT()
                .url(this.resource)
                .entity(this.entity)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
    }

    public <T> T put(Class<T> entity) {
        String result = put();
        return serializer.deserialize(result, entity);
    }

    public String get() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom()
                        .setConnectTimeout(GENERIC_TIMEOUT)
                        .setSocketTimeout(timeout)
                        .build();
        return client.getStringTransport().execute(Requester.GET()
                .url(this.resource)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
    }

    public <T> T get(Class<T> entity) {
        String result = get();
        return serializer.deserialize(result, entity);
    }

    public String delete() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom()
                        .setConnectTimeout(GENERIC_TIMEOUT)
                        .setSocketTimeout(timeout)
                        .build();
        return client.getStringTransport().execute(Requester.DELETE()
                .url(this.resource)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build()
        );
    }

    public <T> T delete(Class<T> entity) {
        String result = delete();
        return serializer.deserialize(result, entity);
    }

    /**
     * 注意，这里使用的是POST
     *
     * @return
     */
    public byte[] load() {
        return client.load(this.parameters, this.headers, this.resource);
    }

    public byte[] getBinary() {
        return client.getBinary(this.resource, this.parameters, this.headers);
    }

    public byte[] download() {
        RequestConfig requestConfig = timeout == -1 ? RequestConfig.DEFAULT :
                RequestConfig.custom()
                        .setConnectionRequestTimeout(GENERIC_TIMEOUT)
                        .setConnectTimeout(GENERIC_TIMEOUT)
                        .setSocketTimeout(timeout)
                        .build();
        return client.getBinaryTransport().execute(Requester.GET()
                .url(this.resource)
                .headers(this.headers)
                .parameters(this.parameters)
                .requestConfig(requestConfig)
                .build());
    }
}
