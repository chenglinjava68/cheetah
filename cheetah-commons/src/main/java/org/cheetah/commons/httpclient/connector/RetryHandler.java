package org.cheetah.commons.httpclient.connector;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @author Max
 * @email max@tagsdata.com
 * @date 2014-12-29 上午10:20:03
 * @version 1.0
 */
public class RetryHandler implements HttpRequestRetryHandler {

	@Override
	public boolean retryRequest(IOException exception, int executionCount,
			HttpContext context) {
		if(executionCount > 3)
			return false;
		if(exception instanceof InterruptedIOException)
			return false; //超时
		if(exception instanceof UnknownHostException)
			return false; //目标服务器不可达
		if(exception instanceof ConnectTimeoutException)
			return false;//连接拒绝
		if(exception instanceof SSLException)
			return false; //ssl握手异常
		HttpClientContext clientContext = HttpClientContext.adapt(context);
		HttpRequest req = clientContext.getRequest();
		boolean idmpotent = !(req instanceof HttpEntityEnclosingRequest);
		if(idmpotent)
			return true; //如果请求是幂等的，就再次尝试
		return false;
	}

}
