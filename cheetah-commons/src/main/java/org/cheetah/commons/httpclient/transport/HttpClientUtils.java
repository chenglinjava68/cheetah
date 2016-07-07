package org.cheetah.commons.httpclient.transport;

import com.google.common.io.Closeables;
import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Max
 * @version 1.0
 * @date 2014-12-29 上午9:41:08
 */
public final class HttpClientUtils {

    public static void gzipDecompression(HttpResponse resp) {
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

    public static void close(OutputStream out, InputStream in, HttpRequestBase requestBase) {
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
        requestBase.abort();
        requestBase.releaseConnection();
    }

    public static void setUriParameter(String url, Map<String, String> params, HttpRequestBase requestBase) throws URISyntaxException {
        URIBuilder uri = new URIBuilder(url);
        if (params != null)
            for (Map.Entry<String, String> map : params.entrySet()) {
                uri.addParameter(map.getKey(), map.getValue());
            }
        requestBase.setURI(URI.create(uri.toString()));
    }

    public static void setBody(String body, HttpEntityEnclosingRequestBase requestBase) {
        if (body != null) {
            StringEntity entity = new StringEntity(body, "UTF-8");
            requestBase.setEntity(entity);
        }
    }

    public static void setHeader(Map<String, String> header, HttpRequestBase httpRequestBase) {
        if (header != null)
            for (Map.Entry<String, String> map : header.entrySet()) {
                httpRequestBase.setHeader(map.getKey(), map.getValue());
            }
    }

    public static void setFormParameter(Map<String, String> params, HttpEntityEnclosingRequestBase httpRequestBase)
            throws UnsupportedEncodingException {
        if (params != null) {
            List<NameValuePair> value = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> map : params.entrySet()) {
                value.add(new BasicNameValuePair(map.getKey(), map.getValue()));
            }
            HttpEntity formEntity = new UrlEncodedFormEntity(value, "UTF-8");
            httpRequestBase.setEntity(formEntity);
        }
    }

    public static void close(HttpRequestBase requestBase, CloseableHttpResponse resp) {
        if (requestBase != null) {
            requestBase.abort();
            requestBase.releaseConnection();
        }
        try {
            Closeables.close(resp, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(CloseableHttpResponse httpResponse, HttpEntity entity) {
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Closeables.close(httpResponse, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOK(StatusLine statusLine) {
        return statusLine.getStatusCode() == HttpStatus.SC_OK;
    }

    /**
     * restful成功状态
     *
     * @param statusLine
     * @return
     */
    public static boolean resetSuccessStatus(StatusLine statusLine) {
        return statusLine.getStatusCode() == HttpStatus.SC_OK || statusLine.getStatusCode() == HttpStatus.SC_CREATED
                || statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT || statusLine.getStatusCode() == HttpStatus.SC_ACCEPTED;
    }
}
