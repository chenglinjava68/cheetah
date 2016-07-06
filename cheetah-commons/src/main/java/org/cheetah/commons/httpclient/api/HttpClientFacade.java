package org.cheetah.commons.httpclient.api;

import com.google.common.collect.Maps;
import org.cheetah.commons.httpclient.Transporter;
import org.cheetah.commons.httpclient.transport.BinaryTransport;
import org.cheetah.commons.httpclient.transport.RestTransport;

import java.util.Map;

/**
 * httpclient门面
 * 
 * @author Max
 * @email max@tagsdata.com
 * @date 2014-12-11 下午1:49:01
 * @version 1.0
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
	 * 模拟http post
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @return
	 */
	public String post(String url, Map<String, String> params) {
		return post(url, params, null);
	}

	/**
	 * http post
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @param header
	 *            头信息
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

	/**
	 *
	 * @param params 请求参数
	 * @param header 请求地址
	 * @param url
     * @return
     */
	public byte[] post(Map<String, String> params, Map<String, String> header, String url) {
		return binaryTransport.execute(
				Transporter.POST().url(url)
						.parameters(params)
						.headers(header)
						.build());
	}

	/**
	 * http post
	 * @param params 请求参数
	 * @param url 请求地址
     * @return
     */
	public byte[] post(Map<String, String> params, String url) {
		return post(null, params, url);
	}

	/**
	 * http post entity形式传输数据
	 * 
	 * @param url
	 * @param entity
	 *            传输数据
	 * @param headers
	 *            头信息
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
	 * 模拟http post entity形式传输数据
	 * 
	 * @param url
	 * @param entity
	 *            传输数据
	 * @return
	 */
	public String post(String url, String entity) {
		Map<String, String> headers = Maps.newHashMap();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		return post(url, entity, headers);
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 * @return
	 */
	public String get(String url) {
		return get(url, null);
	}

	/**
	 * 模拟http get请求
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @return
	 */
	public String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * 模拟http get请求
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @param headers
	 *            头信息
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
	
	public byte[] getBinary(String url) {
		return getBinary(url, null);
	}
	
	public byte[] getBinary(String url, Map<String, String> params) {
		return getBinary(url, params, null);
	}

	/**
	 * http get请求，可以用在获取文件或者流数据
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

	public BinaryTransport getBinaryTransport() {
		return binaryTransport;
	}

	public RestTransport getRestfulTransport() {
		return restfulTransport;
	}

}
