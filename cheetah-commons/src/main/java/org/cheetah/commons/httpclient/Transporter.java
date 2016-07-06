package org.cheetah.commons.httpclient;

import com.google.common.collect.Maps;

import java.net.URI;
import java.util.Map;

import static org.cheetah.commons.httpclient.Transporter.METHOD.GET;

/**
 * Created by maxhuang on 2016/7/6.
 */
public class Transporter {

    enum METHOD {
        GET, POST, PUT, DELETE
    }
    private URI url;
    private String body;
    private METHOD method;
    private Map<String, Object> headers = Maps.newHashMap();
    private Map<String, Object> parameters = Maps.newHashMap();

    Transporter() {
    }

    Transporter(Builder builder) {
        this.url = builder.url;
        this.body = builder.body;
        this.method = builder.method;
        this.headers = builder.headers;
        this.parameters = builder.parameters;
    }

    public URI url() {
        return url;
    }

    public METHOD method() {
        return method;
    }

    public String body() {
        return body;
    }

    public Map<String, Object> headers() {
        return headers;
    }

    public Map<String, Object> parameters() {
        return parameters;
    }

    public static class Builder {
        private URI url;
        private String body;
        private METHOD method = GET;
        private Map<String, Object> headers;
        private Map<String, Object> parameters;

        public Transporter build() {
            return new Transporter(this);
        }
        public Builder url(URI url) {
            this.url = url;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder method(METHOD method) {
            this.method = method;
            return this;
        }

        public Builder headers(Map<String, Object> headers) {
            this.headers = headers;
            return this;
        }

        public Builder parameters(Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }
    }
}
