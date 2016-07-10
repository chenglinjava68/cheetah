package org.cheetah.commons.excel.api;

import org.cheetah.commons.utils.Assert;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * excel转换参数
 * Created by Max on 2016/6/25.
 */
public class Translation<T> {
    private Map<String, String> placeholder;
    private List<T> data;
    private Class<T> entity;
    private boolean hasTemplate;
    private boolean isXssf;
    private InputStream templateStream;
    private OutputStream toStream;

    public Translation(Builder<T> builder) {
        Assert.notNull(builder.toStream, "toStream must not be null.");
        Assert.notNull(builder.data, "data must not be null.");
        Assert.notNull(builder.entity, "entity must not be null.");
        this.placeholder = builder.placeholder;
        this.isXssf = builder.isXssf;
        this.data = builder.data;
        this.entity = builder.entity;
        this.hasTemplate = builder.hasTemplate;
        this.templateStream = builder.templateStream;
        this.toStream = builder.toStream;
    }

    public Map<String, String> placeholder() {
        return placeholder;
    }

    public List<T> data() {
        return data;
    }

    public Class<T> entity() {
        return entity;
    }

    public boolean hasTemplate() {
        return hasTemplate;
    }

    public InputStream templateStream() {
        return templateStream;
    }

    public OutputStream toStream() {
        return toStream;
    }

    public boolean xssf() {
        return isXssf;
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        Map<String, String> placeholder;
        List<T> data;
        Class<T> entity;
        boolean isXssf;
        boolean hasTemplate = false;
        InputStream templateStream;
        OutputStream toStream;

        Builder() {
        }

        Builder(Class<T> entity) {
            this.entity = entity;
        }

        public Translation<T> build() {
            return new Translation<>(this);
        }

        public Builder<T> placeholder(Map<String, String> placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder<T> data(List<T> data) {
            this.data = data;
            return this;
        }

        public Builder<T> entity(Class<T> entity) {
            this.entity = entity;
            return this;
        }

        public Builder<T> xssf(boolean xssf) {
            isXssf = xssf;
            return this;
        }

        public Builder<T> templateStream(InputStream templateStream) {
            this.templateStream = templateStream;
            this.hasTemplate = true;
            return this;
        }

        public Builder<T> toStream(OutputStream toStream) {
            this.toStream = toStream;
            return this;
        }
    }

}
