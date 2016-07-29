package org.cheetah.commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * cheetah顶级Exception类
 * Created by Max on 2016/2/1.
 */
public class PlatformException extends RuntimeException implements Serializable {
    public static final String PLATFORM_ERROR = "platform error";
    private static final long serialVersionUID = 4307299885198466202L;
    private String errorCode;
    private String message;
    private List<Object> params;

    public PlatformException(String errorCode) {
        super(errorCode);
        this.params = Collections.emptyList();
        this.errorCode = errorCode;
    }

    public PlatformException(String errorCode, String message) {
        super(errorCode);
        this.errorCode = errorCode;
        this.message = message;
    }

    public PlatformException(String errorCode, String message, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public PlatformException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.params = Collections.emptyList();
        this.errorCode = errorCode;
    }

    public PlatformException(String errorCode, Object... params) {
        this(errorCode, Arrays.asList(params));
    }

    public PlatformException(String errorCode, List<Object> params) {
        super(errorCode);
        this.params = Collections.emptyList();
        this.errorCode = errorCode;
        this.params = params;
    }

    public PlatformException(String errorCode, Throwable cause, Object... params) {
        this(errorCode, cause, Arrays.asList(params));
    }

    public PlatformException(String errorCode, Throwable cause, List<Object> params) {
        super(errorCode, cause);
        this.params = Collections.emptyList();
        this.errorCode = errorCode;
        this.params = params;
    }

    public String getMessage() {
        return this.message() + " " + String.format("[errorCode: %s] ", new Object[]{this.getErrorCode()}) + " ,message: " + this.message();
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public List<Object> getParams() {
        return this.params;
    }

    public String message() {
//        return StringUtils.isBlank(this.errorCode)?"platform error":configuration.getString(this.errorCode, "platform error");
        return this.message;
    }

}
