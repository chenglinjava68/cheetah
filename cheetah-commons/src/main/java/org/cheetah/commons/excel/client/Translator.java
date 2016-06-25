package org.cheetah.commons.excel.client;

import org.cheetah.commons.utils.Assert;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * excel转换参数
 * Created by Max on 2016/6/25.
 */
public class Translator<T> {
    private Map<String, String> basicData;
    private List<T> data;
    private Class<T> entity;
    private boolean hasTemplate;
    private boolean isXssf;
    private InputStream templateStream;
    private OutputStream toStream;

    public Translator(Builder<T> builder) {
        Assert.notNull(builder.toStream, "toStream must not be null.");
        Assert.notNull(builder.data, "data must not be null.");
        Assert.notNull(builder.entity, "entity must not be null.");
        this.basicData = builder.basicData;
        this.isXssf = builder.isXssf;
        this.data = builder.data;
        this.entity = builder.entity;
        this.hasTemplate = builder.hasTemplate;
        this.templateStream = builder.templateStream;
        this.toStream = builder.toStream;
    }

    public Map<String, String> basicData() {
        return basicData;
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

    public static Builder newBuilder() {
        return new Builder<>();
    }

    public static class Builder<E> {
        Map<String, String> basicData;
        List<E> data;
        Class<E> entity;
        boolean isXssf;
        boolean hasTemplate;
        InputStream templateStream;
        OutputStream toStream;

        Builder() {
        }

        Builder(Class<E> entity) {
            this.entity = entity;
        }

        public Translator<E> build() {
            return new Translator<E>(this);
        }

        public Builder basicData(Map<String, String> basicData) {
            this.basicData = basicData;
            return this;
        }

        public Builder data(List<E> data) {
            this.data = data;
            return this;
        }

        public Builder entity(Class<E> entity) {
            this.entity = entity;
            return this;
        }

        public Builder xssf(boolean xssf) {
            isXssf = xssf;
            return this;
        }

        public Builder hasTemplate(boolean hasTemplate) {
            this.hasTemplate = hasTemplate;
            return this;
        }

        public Builder templateStream(InputStream templateStream) {
            this.templateStream = templateStream;
            return this;
        }

        public Builder toStream(OutputStream toStream) {
            this.toStream = toStream;
            return this;
        }
    }

}
