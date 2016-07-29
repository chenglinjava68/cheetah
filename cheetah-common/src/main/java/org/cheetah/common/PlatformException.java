package org.cheetah.common;

import java.io.Serializable;

/**
 * cheetah顶级Exception类
 * Created by Max on 2016/2/1.
 */
public class PlatformException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 4307299885198466202L;
    private int errorCode;
    private String message;
    private ExceptionMapping mapper;

    public PlatformException(int errorCode) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
    }

    public PlatformException(int errorCode, String message) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
        this.message = message;
    }

    public PlatformException(int errorCode, String message, Throwable cause) {
        super(String.valueOf(errorCode), cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public PlatformException(ExceptionMapping mapper) {
        super(mapper.getMessage());
        this.mapper = mapper;
        this.errorCode = mapper.getCode();
        this.message = mapper.getMessage();
    }

    public PlatformException(ExceptionMapping mapper, Throwable cause) {
        super(mapper.getMessage(), cause);
        this.mapper = mapper;
        this.errorCode = mapper.getCode();
        this.message = mapper.getMessage();
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return String.format("[errorCode: %d]  %s", this.errorCode, message());
    }

    public String message() {
        return this.message;
    }

}
