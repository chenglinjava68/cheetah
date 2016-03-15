package cheetah.commons;

/**
 * cheetah顶级Exception类
 * Created by Max on 2016/2/1.
 */
public class CheetahException extends RuntimeException {
    public CheetahException() {
        super();
    }

    public CheetahException(String message) {
        super(message);
    }

    public CheetahException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheetahException(Throwable cause) {
        super(cause);
    }

    protected CheetahException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
