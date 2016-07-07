package org.cheetah.commons.httpclient;

import org.cheetah.commons.httpclient.api.Clients;
import org.junit.Test;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {

    @Test
    public void test() {
        String result  = Clients.resource("http://baidu.com")
                .parameter("a", "a")
                .entity("entity")
                .parameter("b", "b")
                .timeout(2000)
                .post();
    }



}
