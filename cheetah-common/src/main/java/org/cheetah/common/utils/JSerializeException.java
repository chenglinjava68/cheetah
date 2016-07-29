package org.cheetah.common.utils;

import org.cheetah.common.ExceptionMapping;
import org.cheetah.common.PlatformException;

/**
 * Created by Max on 2016/2/28.
 */
public class JSerializeException extends PlatformException {
    private static final long serialVersionUID = 2336359841576111685L;

    public JSerializeException(int errorCode) {
        super(errorCode);
    }

    public JSerializeException(int errorCode, String message) {
        super(errorCode, message);
    }

    public JSerializeException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public JSerializeException(ExceptionMapping mapper) {
        super(mapper);
    }

    public JSerializeException(ExceptionMapping mapper, Throwable cause) {
        super(mapper, cause);
    }
}
