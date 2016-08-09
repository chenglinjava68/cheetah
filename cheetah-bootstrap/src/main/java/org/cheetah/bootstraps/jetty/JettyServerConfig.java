package org.cheetah.bootstraps.jetty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cheetah.common.utils.Assert;

/**
 * Created by Max on 2016/6/22.
 */
public final class JettyServerConfig {
    private String host;
    private int port;
    private int timeout;
    private String contextPath;
    private int acceptQueueSize;
    private int minThreads;
    private int maxThreads;
    private String descriptor;
    private String webappPath;

    JettyServerConfig() {
    }

    JettyServerConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.timeout = builder.timeout;
        this.contextPath = builder.contextPath;
        this.acceptQueueSize = builder.acceptQueueSize;
        this.minThreads = builder.minThreads;
        this.maxThreads = builder.maxThreads;
        this.descriptor = builder.descriptor;
        this.webappPath = builder.webappPath;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public int timeout() {
        return timeout;
    }

    public String contextPath() {
        return contextPath;
    }

    public int acceptQueueSize() {
        return acceptQueueSize;
    }

    public int minThreads() {
        return minThreads;
    }

    public int maxThreads() {
        return maxThreads;
    }

    public String descriptor() {
        return descriptor;
    }

    public String webappPath() {
        return webappPath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    public static class Builder {
        String host;
        int port = 8000;
        int timeout = 30000;
        String contextPath;
        int acceptQueueSize = 512;
        int minThreads = Runtime.getRuntime().availableProcessors() * 2;
        int maxThreads = 256;
        String descriptor;
        String webappPath;

        public JettyServerConfig build() {
            return new JettyServerConfig(this);
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            if(port <= 0)
                throw new IllegalArgumentException(" port must be greater than 0");
            this.port = port;
            return this;
        }

        public Builder timeout(int timeout) {
            if(timeout <= 0)
                throw new IllegalArgumentException(" timeout must be greater than 0");
            this.timeout = timeout;
            return this;
        }

        public Builder contextPath(String contextPath) {
            Assert.notBlank(contextPath, "contextPath must not be null or empty");
            this.contextPath = contextPath;
            return this;
        }

        public Builder acceptQueueSize(int acceptQueueSize) {
            if(acceptQueueSize <= 0)
                throw new IllegalArgumentException(" acceptQueueSize must be greater than 0");
            this.acceptQueueSize = acceptQueueSize;
            return this;
        }

        public Builder minThreads(int minThreads) {
            if(minThreads <= 0)
                throw new IllegalArgumentException(" minThreads must be greater than 0");
            this.minThreads = minThreads;
            return this;
        }

        public Builder maxThreads(int maxThreads) {
            if(maxThreads <= 0)
                throw new IllegalArgumentException(" maxThreads must be greater than 0");
            this.maxThreads = maxThreads;
            return this;
        }

        public Builder descriptor(String descriptor) {
            Assert.notBlank(descriptor, "descriptor must not be null or empty");
            this.descriptor = descriptor;
            return this;
        }

        public Builder webappPath(String webappPath) {
            Assert.notBlank(webappPath, "webappPath must not be null or empty");
            this.webappPath = webappPath;
            return this;
        }

    }
}
