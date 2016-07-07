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
                .type("content-type", "application/json")
                .type("accept", "application/json")
                .parameter("a", "a")
                .entity("entity")
                .parameter("b", "b")
                .post();
        System.out.println(result);
    }



}
