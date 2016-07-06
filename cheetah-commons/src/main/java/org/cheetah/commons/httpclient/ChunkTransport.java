package org.cheetah.commons.httpclient;

import org.apache.http.client.HttpClient;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Max on 2015/11/26.
 */
public interface ChunkTransport {

    void download(HttpClient httpClient, String url, String toPath);

    void  download(OutputStream stream, HttpClient httpClient, String url);

    InputStream download(HttpClient httpClient, String url);
}
