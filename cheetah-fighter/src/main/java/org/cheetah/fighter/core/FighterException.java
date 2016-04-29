package org.cheetah.fighter.core;

/**
 * Created by Max on 2016/4/30.
 */
public class FighterException extends RuntimeException {
    public FighterException() {
    }

    public FighterException(String message) {
        super(message);
    }

    public FighterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FighterException(Throwable cause) {
        super(cause);
    }

    public FighterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
