package org.cheetah.commons.httpclient;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created by Max on 2015/11/26.
 */
public interface HttpTransport {

    T transport(Transporter transporter, ResponseCallback<T> callback);

}
