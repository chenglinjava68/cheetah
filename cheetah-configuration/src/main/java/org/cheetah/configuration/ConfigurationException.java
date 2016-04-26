package org.cheetah.configuration;

/**
 * Created by Max on 2016/4/26.
 */
public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 7575811996971125440L;

    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
