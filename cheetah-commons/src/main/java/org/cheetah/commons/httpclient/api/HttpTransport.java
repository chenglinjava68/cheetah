package org.cheetah.commons.httpclient.api;

/**
 * http运输接口
 * Created by Max on 2015/11/26.
 */
public interface HttpTransport<T> {
    /**
     * 请求执行方法
     * @param requester 请求者
     * @return
     */
    T execute(Requester requester);

    /**
     * 请求执行方法
     * @param requester 请求者
     * @param handler 响应处理器
     * @return
     */
    T execute(Requester requester, ResponseHandler<T> handler);

}
