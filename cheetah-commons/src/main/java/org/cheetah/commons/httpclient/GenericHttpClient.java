package org.cheetah.commons.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础httpclient访问器
 * 
 * @author Max
 * @email max@tagsdata.com
 * @date 2014-12-11 下午1:49:01
 * @version 1.0
 */
public class GenericHttpClient {
	private CloseableHttpClient httpclient;
	private BinaryHttpTransport binaryHttpTransport;
	private RestfulHttpTransport restfulHttpTransport;
	private ChunkHttpTransport chunkHttpTransport;

	public GenericHttpClient() {
		HttpTransportBuilder builder = HttpTransportBuilder.newBuilder();
		httpclient = builder.buildhttpclient();
		binaryHttpTransport = builder.buildBinaryHttpTransport();
		restfulHttpTransport = builder.buildRestfulHttpTransport();
		chunkHttpTransport = builder.buildChunkHttpTransport();
	}

	public CloseableHttpClient httpclient() {
		return httpclient;
	}

	/**
	 * 模拟http post请求不带参数
	 * 
	 * @param url
	 * @return
	 */
	public String post(String url) {
		return restfulHttpTransport.post(this.httpclient, url, new HashMap<String, String>(),
				null);
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
		return restfulHttpTransport.post(this.httpclient, url, new HashMap<String, String>(),
				null);
	}

	/**
	 * 模拟http post
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
		return restfulHttpTransport.post(this.httpclient, url, params, header);
	}
	
	public byte[] postToByte(String url, Map<String, String> params,
			Map<String, String> header) {
		return binaryHttpTransport.post(this.httpclient, url, params, header);
	}
	
	public byte[] postToByte(String url) {
		return binaryHttpTransport.post(this.httpclient, url, null, null);
	}
	
	public byte[] postToByte(String url, Map<String, String> params) {
		return binaryHttpTransport.post(this.httpclient, url, params, null);
	}

	/**
	 * 模拟http post body形式传输数据
	 * 
	 * @param url
	 * @param body
	 *            传输数据
	 * @param header
	 *            头信息
	 * @return
	 */
	public String post(String url, String body, Map<String, String> header) {
		return restfulHttpTransport.post(this.httpclient, url, body, header);
	}

	/**
	 * 模拟http post body形式传输数据
	 * 
	 * @param url
	 * @param body
	 *            传输数据
	 * @return
	 */
	public String post(String url, String body) {
		return restfulHttpTransport.post(this.httpclient, url, body, null);
	}

	/**
	 * 模拟http get请求
	 * 
	 * @param url
	 * @return
	 */
	public String get(String url) {
		return restfulHttpTransport.get(this.httpclient, url, null, null);
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
		return restfulHttpTransport.get(this.httpclient, url, params, null);
	}

	/**
	 * 模拟http get请求
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @param header
	 *            头信息
	 * @return
	 */
	public String get(String url, Map<String, String> params,
			Map<String, String> header) {
		return restfulHttpTransport.get(this.httpclient, url, params, header);
	}
	
	public byte[] getToByte(String url) {
		return binaryHttpTransport.get(this.httpclient, url, null, null);
	}
	
	public byte[] getToByte(String url, Map<String, String> params) {
		return binaryHttpTransport.get(this.httpclient, url, params, null);
	}
	
	public byte[] getToByte(String url, Map<String, String> params,
			Map<String, String> header) {
		return binaryHttpTransport.get(this.httpclient, url, params, header);
	}

	/**
	 * 模拟http get请求下载至一个系统文件
	 * 
	 * @param url
	 * @param path
	 *            文件输出路径
	 * @return
	 */
	public void download(String url, String path) {
		chunkHttpTransport.download(this.httpclient, url, path);
	}
	
	/**
	 * 模拟http get请求下载文件到outputstream中
	 * 
	 * @param url
	 * @return
	 */
	public InputStream download(String url) {
		return chunkHttpTransport.download(this.httpclient, url);
	}

	public void download(String url, OutputStream destOs) {
		chunkHttpTransport.download(destOs, this.httpclient, url);
	}

	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public BinaryHttpTransport getBinaryHttpTransport() {
		return binaryHttpTransport;
	}

	public void setBinaryHttpTransport(BinaryHttpTransport binaryHttpTransport) {
		this.binaryHttpTransport = binaryHttpTransport;
	}

	public RestfulHttpTransport getRestfulHttpTransport() {
		return restfulHttpTransport;
	}

	public void setRestfulHttpTransport(RestfulHttpTransport restfulHttpTransport) {
		this.restfulHttpTransport = restfulHttpTransport;
	}

	public ChunkHttpTransport getChunkHttpTransport() {
		return chunkHttpTransport;
	}

	public void setChunkHttpTransport(ChunkHttpTransport chunkHttpTransport) {
		this.chunkHttpTransport = chunkHttpTransport;
	}
}
