package org.cheetah.commons.httpclient;

/**
 * Created by Max on 2015/11/26.
 */
public class HttpGetException extends RuntimeException {
    public HttpGetException() {
        super();
    }

    public HttpGetException(String message, Exception e) {
        super(message, e);
    }
}
