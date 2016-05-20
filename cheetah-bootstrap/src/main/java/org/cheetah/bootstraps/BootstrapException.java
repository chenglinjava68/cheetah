package org.cheetah.bootstraps;

/**
 * @author siuming
 */
public class BootstrapException extends RuntimeException{
    private static final long serialVersionUID = 8866164572690003382L;

    public BootstrapException(String message, Throwable cause) {
        super(message, cause);
    }
}
