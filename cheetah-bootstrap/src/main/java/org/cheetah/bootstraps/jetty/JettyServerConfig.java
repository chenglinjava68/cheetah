package org.cheetah.bootstraps.jetty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Max on 2016/6/22.
 */
public final class JettyServerConfig {
    private int port;
    private long timeout;
    private String contextPath;
    private int acceptQueueSize;
    private int minThreads;
    private int maxThreads;
    private String descriptor;
    private String webappPath;

    JettyServerConfig() {
    }

    JettyServerConfig(Builder builder) {
        this.port = builder.port;
        this.timeout = builder.timeout;
        this.contextPath = builder.contextPath;
        this.acceptQueueSize = builder.acceptQueueSize;
        this.minThreads = builder.minThreads;
        this.maxThreads = builder.maxThreads;
        this.descriptor = builder.descriptor;
        this.webappPath = builder.webappPath;
    }

    public int port() {
        return port;
    }

    public long timeout() {
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
        int port;
        long timeout;
        String contextPath;
        int acceptQueueSize;
        int minThreads;
        int maxThreads;
        String descriptor;
        String webappPath;

        public JettyServerConfig build() {
            return new JettyServerConfig(this);
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder contextPath(String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public Builder acceptQueueSize(int acceptQueueSize) {
            this.acceptQueueSize = acceptQueueSize;
            return this;
        }

        public Builder minThreads(int minThreads) {
            this.minThreads = minThreads;
            return this;
        }

        public Builder maxThreads(int maxThreads) {
            this.maxThreads = maxThreads;
            return this;
        }

        public Builder descriptor(String descriptor) {
            this.descriptor = descriptor;
            return this;
        }

        public Builder webappPath(String webappPath) {
            this.webappPath = webappPath;
            return this;
        }
    }
}
