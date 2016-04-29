package org.cheetah.fighter.worker.support;


import org.cheetah.fighter.core.FighterException;

/**
 * Created by Max on 2016/3/8.
 */
public class InterceptorExecutionException extends FighterException {
    public InterceptorExecutionException() {
        super("");
    }

    public InterceptorExecutionException(String message) {
        super(message);
    }

    public InterceptorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterceptorExecutionException(Throwable cause) {
        super("", cause);
    }

    public InterceptorExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
