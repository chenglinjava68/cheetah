package org.cheetah.fighter.worker;


import org.cheetah.fighter.core.FighterException;

/**
 * Created by Max on 2016/3/8.
 */
public class InterceptorExecutionException extends FighterException {
    public InterceptorExecutionException() {
        super("interceptor invoke Exception");
    }

    public InterceptorExecutionException(String message) {
        super(message);
    }

    public InterceptorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterceptorExecutionException(Throwable cause) {
        super("interceptor invoke Exception", cause);
    }

    public InterceptorExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
