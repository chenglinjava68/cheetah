package org.cheetah.commons.httpclient;

import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Max
 * @email max@tagsdata.com
 * @date 2014-12-29 上午9:41:08
 * @version 1.0
 */
final class HttpClientUtils {

	static void gzipDecompression(HttpResponse resp) {
		HttpEntity entity = resp.getEntity();
		if (entity != null) {
			Header ceheader = entity.getContentEncoding();
			if (ceheader != null)
				for (HeaderElement e : ceheader.getElements()) {
					if (e.getName().equalsIgnoreCase("gzip")) {
						resp.setEntity(new GzipDecompressingEntity(resp
								.getEntity()));
						return;
					}
				}
		}
	}

	static void close(OutputStream out, InputStream in, HttpGet get) {
		try {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		get.abort();
		get.releaseConnection();
	}

	static HttpGet setParameter(String url, Map<String, String> params,
									  Map<String, String> header) throws URISyntaxException {
		HttpGet get;
		URIBuilder uri = new URIBuilder(url);
		if (params != null)
			for (Map.Entry<String, String> map : params.entrySet()) {
				uri.addParameter(map.getKey(), map.getValue());
			}
		get = new HttpGet(uri.toString());
		if (header != null)
			for (Map.Entry<String, String> map : header.entrySet()) {
				get.setHeader(map.getKey(), map.getValue());
			}
		return get;
	}

	static void close(HttpGet get, HttpEntity httpEntity,
						  CloseableHttpResponse resp) {
		try {
			if (httpEntity != null)
				EntityUtils.consume(httpEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (get != null) {
			get.abort();
			get.releaseConnection();
		}
		try {
			if (resp != null)
				resp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void setBody(String body,
								Map<String, String> header, HttpPost post) {
		StringEntity entity = new StringEntity(body, "UTF-8");
		post.setEntity(entity);
		if (header != null)
			for (Map.Entry<String, String> map : header.entrySet()) {
				post.setHeader(map.getKey(), map.getValue());
			}
	}

	static void setParameter(Map<String, String> params,
								Map<String, String> header, HttpPost post)
			throws UnsupportedEncodingException {
		if (params != null) {
			List<NameValuePair> value = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> map : params.entrySet()) {
				value.add(new BasicNameValuePair(map.getKey(), map.getValue()));
			}
			HttpEntity formEntity = new UrlEncodedFormEntity(value, "UTF-8");
			post.setEntity(formEntity);
		}
		if (header != null)
			for (Map.Entry<String, String> map : header.entrySet()) {
				post.setHeader(map.getKey(), map.getValue());
			}
	}

	static void close(HttpPost post, CloseableHttpResponse resp,
						   HttpEntity httpEntity) {
		try {
			if (httpEntity != null)
				EntityUtils.consume(httpEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (post != null) {
			post.abort();
			post.releaseConnection();
		}
		try {
			if (resp != null)
				resp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
