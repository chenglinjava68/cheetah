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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                out = new FileOutputStream(new File(toPath));
                in = resp.getEntity().getContent();
                byte[] buff = new byte[1024];
                int j = 0;
                while ((j = in.read(buff, 0, 1024)) != -1) {
                    out.write(buff, 0, j);
                }
            }
        } catch (Exception e) {
            logger.info("request error!", e);
            e.printStackTrace();
        } finally {
            HttpClientUtils.close(out, in, get);
        }
    }

    @Override
    public InputStream download(HttpClient httpClient, String url) {
        InputStream in = null;
        HttpGet get = null;
        try {
            logger.info("request url : " + url);
            URIBuilder uri = new URIBuilder(url);
            get = new HttpGet(uri.toString());
            HttpResponse resp = httpClient.execute(get);
            StatusLine statusLine = resp.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                in = resp.getEntity().getContent();
            }
        } catch (Exception e) {
            logger.info("request error!", e);
            e.printStackTrace();
        } finally {
            HttpClientUtils.close(null, null, get);
        }
        return in;
    }
}
