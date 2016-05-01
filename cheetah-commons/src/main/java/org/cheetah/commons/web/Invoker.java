package org.cheetah.commons.web;


import org.cheetah.commons.PlatformException;

/**
 * 调用结果
 * Created by Max on 15/1/20.
 */
public class Invoker<T> {
    private T data;
    private boolean hasErrors = false;
    private String errorCode = "";
    private String errorMsg = "";

    private Invoker(T data) {
        this.data = data;
    }

    private Invoker(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.hasErrors = true;
    }

    private Invoker() {

    }

    public T getData() {
        return data;
    }

    public Invoker<T> data(T t) {
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

    public static <T> Invoker<T> success(T data) {
        return new Invoker<T>(data);
    }

    public static <T> Invoker<T> failure(String errorCode) {
        return new Invoker<T>(errorCode, "");
    }

    public static <T> Invoker<T> failure(String errorCode, String errorMsg) {
        return new Invoker<T>(errorCode, errorMsg);
    }

    public static <T> Invoker<T> failure(PlatformException e) {
        return new Invoker<T>(e.getErrorCode(), e.getMessage());
    }

    public static Invoker<Void> voidResult() {
        return new Invoker<Void>();
    }
}
