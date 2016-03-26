package cheetah.predator.core.support;

import cheetah.commons.Constants;
import cheetah.commons.net.QueryStringDecoder;
import cheetah.commons.utils.Assert;
import com.google.common.base.Strings;

import java.net.URI;
import java.util.List;

/**
 * @author Max
 */
public final class SessionTransportConfig {

    private final URI accessEndpoint;

    /**
     * @param accessEndpoint
     */
    public SessionTransportConfig(String accessEndpoint) {
        Assert.hasText(accessEndpoint, "accessEndpoint must not be null or empty.");
        this.accessEndpoint = URI.create(accessEndpoint).normalize();
    }

    /**
     * @return
     */
    public String scheme() {
        return accessEndpoint.getScheme();
    }

    /**
     * @return
     */
    public String host() {
        return accessEndpoint.getHost();
    }

    /**
     * @return
     */
    public int port() {
        return accessEndpoint.getPort();
    }

    /**
     * @return
     */
    public String path() {
        return accessEndpoint.getPath();
    }

    /**
     * @return
     */
    public URI accessEndpoint() {
        return accessEndpoint;
    }

    /**
     * @param name
     * @return
     */
    public String param(String name) {
        return paramOrDefault(name, "");
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public String paramOrDefault(String name, String defaultValue) {
        List<String> params = new QueryStringDecoder(accessEndpoint).parameters().get(name);
        return null == params || params.isEmpty() ? defaultValue : params.get(0);
    }

    public int intParam(String name) {
        return intParamOrDefault(name, -1);
    }

    public int intParamOrDefault(String name, int defaultValue) {
        String value = param(name);
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }

    public long longParam(String name) {
        return longParamOrDefault(name, -1L);
    }

    public long longParamOrDefault(String name, long defaultValue) {
        String value = param(name);
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }

        return Long.parseLong(value);
    }

    public float floatParam(String name) {
        return floatParamOrDefault(name, -1f);
    }

    public float floatParamOrDefault(String name, float defaultValue) {
        String value = param(name);
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }

        return Float.parseFloat(value);
    }

    public boolean booleanParam(String name) {
        return booleanParamOrDefault(name, false);
    }

    public boolean booleanParamOrDefault(String name, boolean defaultValue) {
        String value = param(name);
        if (Strings.isNullOrEmpty(value)) {
            return defaultValue;
        }

        return Boolean.valueOf(value);
    }

    public int getIdleTimeout() {
        return intParamOrDefault(Constants.IDLE_TIMEOUT_KEY, getIdleCheckPeriod() * 3);
    }

    public int getIdleInitTimeout() {
        return intParamOrDefault(Constants.IDLE_INIT_TIMEOUT_KEY, getIdleCheckPeriod());
    }

    public int getIdleCheckPeriod() {
        return intParamOrDefault(Constants.IDLE_PERIOD_KEY, 180);
    }

    public long getTrafficLimit() {
        return longParamOrDefault(Constants.TRAFFIC_LIMIT_KEY, 16384L);
    }

    public long getTrafficCheckInterval() {
        return longParamOrDefault(Constants.TRAFFIC_CHECK_INTERVAL_KEY, 16000L);
    }
}
