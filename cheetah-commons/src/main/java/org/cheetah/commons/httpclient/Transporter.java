package org.cheetah.commons.httpclient;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

import static org.cheetah.commons.httpclient.Transporter.METHOD.GET;

/**
 * Created by maxhuang on 2016/7/6.
 */
public class Transporter {

    public enum METHOD {
        GET, POST, PUT, DELETE, HEAD, TRACE, OPTIONS
    }
    private String url;
    private String entity;
    private METHOD method;
    private Map<String, String> headers = Maps.newHashMap();
    private Map<String, String> parameters = Maps.newHashMap();

    Transporter() {
    }

    Transporter(Builder builder) {
        this.url = builder.url;
        this.entity = builder.entity;
        this.method = builder.method;
        this.headers = builder.headers;
        this.parameters = builder.parameters;
    }

    public String url() {
        return url;
    }

    public METHOD method() {
        return method;
    }

    public String entity() {
        return entity;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, String> parameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static Builder POST() {
        return new Builder(METHOD.POST);
    }

    public static Builder DELETE() {
        return new Builder(METHOD.DELETE);
    }

    public static Builder GET() {
        return new Builder(METHOD.GET);
    }

    public static Builder HEAD() {
        return new Builder(METHOD.HEAD);
    }

    public static Builder OPTIONS() {
        return new Builder(METHOD.OPTIONS);
    }

    public static Builder PUT() {
        return new Builder(METHOD.PUT);
    }

    public static Builder TRACE() {
        return new Builder(METHOD.TRACE);
    }

    public static class Builder {
        private String url;
        private String entity;
        private METHOD method = GET;
        private Map<String, String> headers;
        private Map<String, String> parameters;

        Builder(METHOD method) {
            this.method = method;
        }

        public Transporter build() {
            return new Transporter(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder entity(String entity) {
            this.entity = entity;
            return this;
        }

        public Builder method(METHOD method) {
            this.method = method;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder parameters(Map<String, String> parameters) {
            this.parameters = parameters;
            return this;
        }
    }
}
