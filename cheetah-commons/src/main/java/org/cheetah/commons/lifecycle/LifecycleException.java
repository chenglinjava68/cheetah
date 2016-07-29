package org.cheetah.commons.lifecycle;

/**
 * <p>LifecycleException class.</p>
 *
 * @author Max
 * @version $Id: $Id
 */
public class LifecycleException extends RuntimeException {
    private static final long serialVersionUID = 6333262728735118802L;

    /**
     * <p>Constructor for LifecycleException.</p>
     *
     * @param message a {@link String} object.
     */
    public LifecycleException(String message) {
        super(message);
    }
}
