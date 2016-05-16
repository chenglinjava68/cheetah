package org.cheetah.commons.httpclient;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpPostException extends RuntimeException {
    public HttpPostException() {
        super();
    }

    public HttpPostException(String message) {
        super(message);
    }

    public HttpPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
