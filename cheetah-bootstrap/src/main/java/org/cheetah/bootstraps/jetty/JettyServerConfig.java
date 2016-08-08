package org.cheetah.bootstraps.jetty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cheetah.common.utils.Assert;

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
        int port = 8000;
        long timeout = 30000L;
        String contextPath;
        int acceptQueueSize = 512;
        int minThreads = Runtime.getRuntime().availableProcessors() * 2;
        int maxThreads = 256;
        String descriptor = "./webapp/WEB-INF/web.xml";
        String webappPath = "./webapp";

        public JettyServerConfig build() {
            return new JettyServerConfig(this);
        }

        public Builder port(int port) {
            if(port <= 0)
                throw new IllegalArgumentException(" port must be greater than 0");
            this.port = port;
            return this;
        }

        public Builder timeout(long timeout) {
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
