package org.cheetah.common.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * 响应处理器
 * Created by maxhuang on 2016/7/6.
 */
public interface ResponseHandler<T> {
    /**
     * 当http请求发出，得到响应后就会进入该方法
     * 当如果需要判断响应码、处理响应数据可以实现该接口
     * @param response
     * @return
     */
    T handle(CloseableHttpResponse response);
}
