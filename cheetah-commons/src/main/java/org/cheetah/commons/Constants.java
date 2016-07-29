package org.cheetah.commons;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;

/**
 * <p>Constants class.</p>
 *
 * @author Max
 * @version $Id: $Id
 */
public final class Constants {

    /** Constant <code>DEFAULT_CHARSET</code> */
    public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    /** Constant <code>ID_KEY="id"</code> */
    public static final String ID_KEY = "id";
    /** Constant <code>WEIGHT_KEY="weight"</code> */
    public static final String WEIGHT_KEY = "weight";
    /** Constant <code>VERSION_KEY="version"</code> */
    public static final String VERSION_KEY = "version";
    /** Constant <code>SSL_PORT_KEY="sslport"</code> */
    public static final String SSL_PORT_KEY = "sslport";
    /** Constant <code>PLATFORMS_KEY="platforms"</code> */
    public static final String PLATFORMS_KEY = "platforms";
    /** Constant <code>SERVICE_HOST_KEY="service.host"</code> */
    public static final String SERVICE_HOST_KEY = "service.host";
    /** Constant <code>SERVICE_PORT_KEY="service.port"</code> */
    public static final String SERVICE_PORT_KEY = "service.port";

    /** Constant <code>IDLE_TIMEOUT_KEY="idle.timeout"</code> */
    public static final String IDLE_TIMEOUT_KEY = "idle.timeout";
    /** Constant <code>IDLE_INIT_TIMEOUT_KEY="idle.init.timeout"</code> */
    public static final String IDLE_INIT_TIMEOUT_KEY = "idle.init.timeout";
    /** Constant <code>IDLE_PERIOD_KEY="idle.period"</code> */
    public static final String IDLE_PERIOD_KEY = "idle.period";
    /** Constant <code>READ_LIMIT_KEY="read.limit"</code> */
    public static final String TRAFFIC_LIMIT_KEY = "traffic.limit";
    public static final String TRAFFIC_CHECK_INTERVAL_KEY = "traffic.check.interval";


    private Constants() {
    }
}
