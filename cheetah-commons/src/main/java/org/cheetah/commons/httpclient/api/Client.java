package org.cheetah.commons.httpclient.api;

import com.google.common.collect.ImmutableMap;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.StringTransport;

import java.util.Map;

/**
 * http客户端接口
 * Created by Max on 2016/7/8.
 */
public interface Client {
    Map<String, String> RESET_TYPE = ImmutableMap.of("Content-Type", "application/json", "Accept", "application/json");

    BinaryTransport getBinaryTransport();

    StringTransport getStringTransport();

    <T> T execute(Requester requester, ResponseHandler<T> handler);

    String post(String url);

    /**
     * http post
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    String post(String url, Map<String, String> params);

    /**
     * http post
     *
     * @param url
     * @param params 请求参数
     * @param header 头信息
     * @return
     */
    String post(String url, Map<String, String> params,
                       Map<String, String> header);

    String post(String url, ResponseHandler<String> handler);

    String post(String url, Map<String, String> params, ResponseHandler<String> handler);

    /**
     * 通过responseHandler自定义处理响应的结果
     *
     * @param url
     * @param params
     * @param header
     * @param handler
     * @return
     */
    String post(String url, Map<String, String> params,
                       Map<String, String> header, ResponseHandler<String> handler);

    /**
     * @param url
     * @return
     */
    byte[] load(String url, ResponseHandler<byte[]> handler);

    /**
     * @param params 请求参数
     * @param url
     * @return
     */
    byte[] load(Map<String, String> params, String url, ResponseHandler<byte[]> handler);

    /**
     * @param params 请求参数
     * @param header 请求地址
     * @param url
     * @return
     */
    byte[] load(Map<String, String> params, Map<String, String> header, String url, ResponseHandler<byte[]> handler);

    /**
     * http post
     *
     * @param params 请求参数
     * @param url    请求地址
     * @return
     */
    byte[] load(Map<String, String> params, String url);


    /**
     * http post
     *
     * @param url 请求地址
     * @return
     */
    byte[] load(String url);

    /**
     * @param params 请求参数
     * @param header 请求地址
     * @param url
     * @return
     */
    byte[] load(Map<String, String> params, Map<String, String> header, String url);

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    String post(String url, String entity, Map<String, String> headers);

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity 传输数据
     * @return
     */
    String post(String url, String entity);

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity 传输数据
     * @return
     */
    String post(String url, String entity, ResponseHandler<String> handler);

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    String post(String url, String entity, Map<String, String> headers, ResponseHandler<String> handler);

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    String post(String url, String entity, Map<String, String> params,
                Map<String, String> headers, ResponseHandler<String> handler);

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    String get(String url);

    /**
     * http get请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    String get(String url, Map<String, String> params);

    /**
     * http get请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 头信息
     * @return
     */
    String get(String url, Map<String, String> params,
                      Map<String, String> headers);

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @return
     */
    String get(String url, ResponseHandler<String> handler);

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    String get(String url, Map<String, String> params, ResponseHandler<String> handler);

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @param params  请求参数
     * @param headers 头信息
     * @return
     */
    String get(String url, Map<String, String> params,
                      Map<String, String> headers, ResponseHandler<String> handler);

    byte[] getBinary(String url);

    byte[] getBinary(String url, Map<String, String> params);

    /**
     * http get请求，可以用在获取文件或者流数据
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    byte[] getBinary(String url, Map<String, String> params,
                            Map<String, String> headers);

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @return
     */
    byte[] getBinary(String url, ResponseHandler<byte[]> handler);

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @param params
     * @return
     */
    byte[] getBinary(String url, Map<String, String> params, ResponseHandler<byte[]> handler);

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    byte[] getBinary(String url, Map<String, String> params,
                            Map<String, String> headers, ResponseHandler<byte[]> handler);
}
