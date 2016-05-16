package org.cheetah.commons.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                out = new FileOutputStream(new File(toPath));
                resp.getEntity().writeTo(out);
            }
        } catch (Exception e) {
            logger.info("request error!", e);
            e.printStackTrace();
        } finally {
            HttpClientUtils.close(out, null, get);
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
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                in = resp.getEntity().getContent();
                resp.getEntity().writeTo(stream);
            }
        } catch (Exception e) {
            logger.info("request error!", e);
            e.printStackTrace();
        } finally {
            HttpClientUtils.close(null, in, get);
        }
    }

    @Override
    public InputStream download(HttpClient httpClient, String url) {
        ByteArrayOutputStream bos = null;
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
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                resp.getEntity().writeTo(bos);
            }
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (Exception e) {
            logger.info("request error!", e);
            e.printStackTrace();
        } finally {
            HttpClientUtils.close(null, null, get);
        }
        return null;
    }


}
