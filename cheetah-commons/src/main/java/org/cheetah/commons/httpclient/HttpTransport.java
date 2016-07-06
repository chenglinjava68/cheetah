package org.cheetah.commons.httpclient;

/**
 * Created by Max on 2015/11/26.
 */
public interface HttpTransport<T> {

    T execute(Transporter transporter);

}
