package org.cheetah.commons.httpclient;

import org.apache.http.client.HttpClient;

import java.io.InputStream;

/**
 * Created by Max on 2015/11/26.
 */
public interface ChunkHttpTransport {

    void download(HttpClient httpClient, String url, String toPath);

    InputStream download(HttpClient httpClient, String url);
}
