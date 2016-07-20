package org.cheetah.commons.web;


import org.cheetah.commons.PlatformException;

/**
 * 调用结果
 * Created by Max on 15/1/20.
 */
public class InvokeResult<T> {
    private T data;
    private boolean hasErrors = false;
    private String errorCode = "";
    private String errorMsg = "";

    private InvokeResult(T data) {
        this.data = data;
    }

    private InvokeResult(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.hasErrors = true;
    }

    private InvokeResult() {

    }

    public T getData() {
        return data;
    }

    public InvokeResult<T> data(T t) {
        this.data = t;
        return this;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public String errorCode() {
        return errorCode;
    }

    public String errorMsg() {
        return errorMsg;
    }

    public boolean successful() {
        return !hasErrors();
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static <T> InvokeResult<T> success(T data) {
        return new InvokeResult<T>(data);
    }

    public static <T> InvokeResult<T> failure(String errorCode) {
        return new InvokeResult<T>(errorCode, "");
    }

    public static <T> InvokeResult<T> failure(String errorCode, String errorMsg) {
        return new InvokeResult<T>(errorCode, errorMsg);
    }

    public static <T> InvokeResult<T> failure(PlatformException e) {
        return new InvokeResult<T>(String.valueOf(e.getErrorCode()), e.getMessage());
    }

    public static InvokeResult<Void> voidResult() {
        return new InvokeResult<Void>();
    }
}
