package org.cheetah.commons.httpclient;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.cheetah.commons.utils.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class WebResource {
    private URI resource;
    private String body;
    private Map<String, Object> headers = Maps.newHashMap();
    private Map<String, Object> parameters = Maps.newHashMap();
    private Object entity;

    public WebResource(String resource) throws URISyntaxException {
        this.resource = new URI(resource);
    }

    public WebResource type(String key, Object value) {
        Assert.notBlank(key, "key must not be null or empty");
        Assert.notNull(value, "value must not be null");
        headers.put(key, value);
        return this;
    }

    public WebResource parameter(String key, Object value) {
        Assert.notBlank(key, "key must not be null or empty");
        Assert.notNull(value, "value must not be null");
        parameters.put(key, value);
        return this;
    }

    public WebResource body(String body) {
        this.body = body;
        return this;
    }

    public String body() {
        return body;
    }

    public URI resource() {
        return resource;
    }

    public Map<String, Object> headers() {
        return ImmutableMap.<String, Object>builder().putAll(headers).build();
    }

    public Map<String, Object> parameters() {
        return ImmutableMap.<String, Object>builder().putAll(parameters).build();
    }

    public Object entity() {
        return entity;
    }
}
