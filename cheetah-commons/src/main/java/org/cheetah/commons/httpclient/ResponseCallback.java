package org.cheetah.commons.httpclient;

import org.apache.http.HttpEntity;

/**
 * Created by maxhuang on 2016/7/6.
 */
public interface ResponseCallback<T> {
    T onResult(HttpEntity entity);
}
