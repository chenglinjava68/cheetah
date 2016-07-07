package org.cheetah.commons.httpclient.api;

import com.google.common.collect.Maps;
import org.cheetah.commons.httpclient.ResponseHandler;
import org.cheetah.commons.httpclient.Transporter;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.RestTransport;

import java.util.Map;

/**
 * httpclient门面
 *
 * @author Max
 * @version 1.0
 * @email max@tagsdata.com
 * @date 2014-12-11 下午1:49:01
 */
public class HttpClientFacade {
    private BinaryTransport binaryTransport;
    private RestTransport restfulTransport;

    public HttpClientFacade() {
    }

    public HttpClientFacade(HttpClientFacadeBuilder builder) {
        this.binaryTransport = builder.binaryHttpTransport;
        this.restfulTransport = builder.restfulHttpTransport;
    }

    /**
     * http post请求不带参数
     *
     * @param url
     * @return
     */
    public String post(String url) {
        return post(url, Maps.newHashMap());
    }

    /**
     * http post
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public String post(String url, Map<String, String> params) {
        return post(url, params, Maps.newHashMap());
    }

    /**
     * http post
     *
     * @param url
     * @param params 请求参数
     * @param header 头信息
     * @return
     */
    public String post(String url, Map<String, String> params,
                       Map<String, String> header) {
        return restfulTransport.execute(
                Transporter.POST().url(url)
                        .parameters(params)
                        .headers(header)
                        .build());
    }

    public String post(String url, ResponseHandler<String> handler) {
        return post(url, Maps.newHashMap(), handler);
    }

    public String post(String url, Map<String, String> params, ResponseHandler<String> handler) {
        return post(url, params, null, handler);
    }

    /**
     * 通过responseHandler自定义处理响应的结果
     *
     * @param url
     * @param params
     * @param header
     * @param handler
     * @return
     */
    public String post(String url, Map<String, String> params,
                       Map<String, String> header, ResponseHandler<String> handler) {
        return restfulTransport.doExecute(
                Transporter.POST().url(url)
                        .parameters(params)
                        .headers(header)
                        .build(), handler);
    }

    /**
     * @param url
     * @return
     */
    public byte[] load(String url, ResponseHandler<byte[]> handler) {
        return load(Maps.newHashMap(), url, handler);
    }

    /**
     * @param params 请求参数
     * @param url
     * @return
     */
    public byte[] load(Map<String, String> params, String url, ResponseHandler<byte[]> handler) {
        return load(params, Maps.newHashMap(), url, handler);
    }

    /**
     * @param params 请求参数
     * @param header 请求地址
     * @param url
     * @return
     */
    public byte[] load(Map<String, String> params, Map<String, String> header, String url, ResponseHandler<byte[]> handler) {
        return binaryTransport.doExecute(
                Transporter.POST().url(url)
                        .parameters(params)
                        .headers(header)
                        .build(), handler);
    }

    /**
     * http post
     *
     * @param params 请求参数
     * @param url    请求地址
     * @return
     */
    public byte[] load(Map<String, String> params, String url) {
        return load(Maps.newHashMap(), params, url);
    }


    /**
     * http post
     *
     * @param url 请求地址
     * @return
     */
    public byte[] load(String url) {
        return load(Maps.newHashMap(), url);
    }

    /**
     * @param params 请求参数
     * @param header 请求地址
     * @param url
     * @return
     */
    public byte[] load(Map<String, String> params, Map<String, String> header, String url) {
        return binaryTransport.execute(
                Transporter.POST().url(url)
                        .parameters(params)
                        .headers(header)
                        .build());
    }
    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    public String post(String url, String entity, Map<String, String> params, Map<String, String> headers) {
        return restfulTransport.execute(
                Transporter.POST().url(url)
                        .entity(entity)
                        .headers(headers)
                        .parameters(params)
                        .build()
        );
    }
    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    public String post(String url, String entity, Map<String, String> headers) {
        return restfulTransport.execute(
                Transporter.POST().url(url)
                        .entity(entity)
                        .headers(headers)
                        .build()
        );
    }

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity 传输数据
     * @return
     */
    public String post(String url, String entity) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return post(url, entity, headers);
    }

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity 传输数据
     * @return
     */
    public String post(String url, String entity, ResponseHandler<String> handler) {
        return post(url, entity, null, handler);
    }

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    public String post(String url, String entity, Map<String, String> headers, ResponseHandler<String> handler) {
        return post(url, entity, null, headers, handler);
    }

    /**
     * http post entity形式传输数据
     *
     * @param url
     * @param entity  传输数据
     * @param headers 头信息
     * @return
     */
    public String post(String url, String entity, Map<String, String> params, Map<String, String> headers, ResponseHandler<String> handler) {
        return restfulTransport.doExecute(
                Transporter.POST().url(url)
                        .entity(entity)
                        .headers(headers)
                        .parameters(params)
                        .build(), handler
        );
    }

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    public String get(String url) {
        return get(url, Maps.newHashMap());
    }

    /**
     * http get请求
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public String get(String url, Map<String, String> params) {
        return get(url, params, Maps.newHashMap());
    }

    /**
     * http get请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 头信息
     * @return
     */
    public String get(String url, Map<String, String> params,
                      Map<String, String> headers) {
        return restfulTransport.execute(Transporter.GET()
                .url(url)
                .parameters(params)
                .headers(headers)
                .build());
    }

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @return
     */
    public String get(String url, ResponseHandler<String> handler) {
        return get(url, null, handler);
    }

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @param params 请求参数
     * @return
     */
    public String get(String url, Map<String, String> params, ResponseHandler<String> handler) {
        return get(url, params, null, handler);
    }

    /**
     * http get请求, 自定义handler处理结果
     *
     * @param url
     * @param params  请求参数
     * @param headers 头信息
     * @return
     */
    public String get(String url, Map<String, String> params,
                      Map<String, String> headers, ResponseHandler<String> handler) {
        return restfulTransport.doExecute(Transporter.GET()
                .url(url)
                .parameters(params)
                .headers(headers)
                .build(), handler);
    }


    public byte[] getBinary(String url) {
        return getBinary(url, Maps.newHashMap());
    }

    public byte[] getBinary(String url, Map<String, String> params) {
        return getBinary(url, params, Maps.newHashMap());
    }

    /**
     * http get请求，可以用在获取文件或者流数据
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public byte[] getBinary(String url, Map<String, String> params,
                            Map<String, String> headers) {
        return binaryTransport.execute(Transporter
                .GET()
                .parameters(params)
                .headers(headers)
                .build());
    }

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @return
     */
    public byte[] getBinary(String url, ResponseHandler<byte[]> handler) {
        return getBinary(url, null, handler);
    }

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @param params
     * @return
     */
    public byte[] getBinary(String url, Map<String, String> params, ResponseHandler<byte[]> handler) {
        return getBinary(url, params, null, handler);
    }

    /**
     * http get请求，自定义handler处理结果
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public byte[] getBinary(String url, Map<String, String> params,
                            Map<String, String> headers, ResponseHandler<byte[]> handler) {
        return binaryTransport.doExecute(Transporter
                .GET()
                .parameters(params)
                .headers(headers)
                .build(), handler);
    }

    public BinaryTransport getBinaryTransport() {
        return binaryTransport;
    }

    public RestTransport getRestfulTransport() {
        return restfulTransport;
    }

}
