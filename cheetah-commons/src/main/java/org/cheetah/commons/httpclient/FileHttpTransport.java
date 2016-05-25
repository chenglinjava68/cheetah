package org.cheetah.commons.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Max on 2015/11/26.
 */
public class FileHttpTransport implements ChunkHttpTransport {
    private final Logger logger = LoggerFactory.getLogger(FileHttpTransport.class);

    @Override
    public void download(HttpClient httpClient, String url, String toPath) {
        FileOutputStream out = null;
        InputStream in = null;
        HttpGet get = null;
        try {
            logger.info("request url : " + url);
            logger.info("request toPath : " + toPath);
            URIBuilder uri = new URIBuilder(url);
            get = new HttpGet(uri.toString());
            HttpResponse resp = httpClient.execute(get);
            for (Header header : resp.getAllHeaders()) {
                logger.info(header.getName() + " --- " + header.getValue());
            }
            StatusLine statusLine = resp.getStatusLine();
            if (HttpClientUtils.status(statusLine)) {
                out = new FileOutputStream(new File(toPath));
                in = resp.getEntity().getContent();
                byte[] buff = new byte[1024];
                int j = 0;
                while ((j = in.read(buff, 0, 1024)) != -1) {
                    out.write(buff, 0, j);
                }
            } else
                throw new HttpPostException("Http get request error[" + statusLine.getStatusCode() + "]-->url : " + url);
        } catch (Exception e) {
            logger.error("request error!", e);
            throw new HttpPostException("download failure", e);
        } finally {
            HttpClientUtils.close(out, in, get);
        }
    }

    @Override
    public void download(OutputStream stream, HttpClient httpClient, String url) {
        InputStream in = null;
        HttpGet get = null;
        try {
            logger.info("request url : " + url);
            URIBuilder uri = new URIBuilder(url);
            get = new HttpGet(uri.toString());
            HttpResponse resp = httpClient.execute(get);
            for (Header header : resp.getAllHeaders()) {
                logger.info(header.getName() + " --- " + header.getValue());
            }
            StatusLine statusLine = resp.getStatusLine();
            if (HttpClientUtils.status(statusLine)) {
                in = resp.getEntity().getContent();
                byte[] buff = new byte[1024];
                int j = 0;
                while ((j = in.read(buff, 0, 1024)) != -1) {
                    stream.write(buff, 0, j);
                }
            } else
                throw new HttpPostException("Http get request error[" + statusLine.getStatusCode() + "]-->url : " + url);
        } catch (Exception e) {
            logger.error("request error!", e);
            throw new HttpPostException("download failure", e);
        } finally {
            HttpClientUtils.close(null, in, get);
        }
    }

    @Override
    public InputStream download(HttpClient httpClient, String url) {
        ByteArrayOutputStream bos = null;
        InputStream in = null;
        HttpGet get = null;
        try {
            logger.info("request url : " + url);
            bos = new ByteArrayOutputStream();
            URIBuilder uri = new URIBuilder(url);
            get = new HttpGet(uri.toString());
            HttpResponse resp = httpClient.execute(get);
            for (Header header : resp.getAllHeaders()) {
                logger.info(header.getName() + " --- " + header.getValue());
            }
            StatusLine statusLine = resp.getStatusLine();
            if (HttpClientUtils.status(statusLine)) {
                in = resp.getEntity().getContent();
                byte[] buff = new byte[1024];
                int j = 0;
                while ((j = in.read(buff, 0, 1024)) != -1) {
                    bos.write(buff, 0, j);
                }
            } else
                throw new HttpPostException("Http get request error[" + statusLine.getStatusCode() + "]-->url : " + url);
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (Exception e) {
            logger.error("request error!", e);
            throw new HttpPostException("download failure", e);
        } finally {
            HttpClientUtils.close(bos, in, get);
        }
    }


}
