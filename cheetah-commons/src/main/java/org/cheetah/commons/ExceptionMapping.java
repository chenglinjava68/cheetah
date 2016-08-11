package org.cheetah.commons;

/**
 * Created by Max on 2016/7/20.
 */
public enum ExceptionMapping {

    SYSTEM(10001, "系统繁忙");

    private int code;
    private String message;

    ExceptionMapping(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
