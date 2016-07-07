package org.cheetah.commons.httpclient;

import org.cheetah.commons.httpclient.api.Clients;
import org.junit.Test;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {

    @Test
    public void post1() {
        String result  = Clients.resource("http://192.168.1.107:8080/test")
                .entity(new User())
                .timeout(2000)
                .post();
    }

    @Test
    public void post2() {
        String result = Clients.resource("http://192.168.1.107:8080/test/form")
                .parameter("username", "user")
                .parameter("password", "pass")
                .timeout(2000)
                .post();
    }

    @Test
    public void get() {
        String result  = Clients.resource("http://192.168.1.107:8080/test/on")
                .get();
    }

}
