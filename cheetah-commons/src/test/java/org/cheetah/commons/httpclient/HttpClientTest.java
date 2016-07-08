package org.cheetah.commons.httpclient;

import org.cheetah.commons.httpclient.api.Clients;
import org.cheetah.commons.httpclient.api.Form;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {

    @Test
    public void post1() {
        String result = Clients.resource("http://localhost:8080/test")
                .entity(new User("user", "pass"))
                .timeout(2000)
                .post();
    }

    @Test
    public void post2() {
        Form form = Form.create().
                parameter("username", "user")
                .parameter("password", "pass");
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "user");
        params.put("passworld", "pass");

        String result = Clients.resource("http://localhost:8080/test/form")
//                .form(form)
                .parameters(params)
                .timeout(2000)
                .post();
    }

    @Test
    public void get() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", "user");
        params.put("pass", "pass");
        String result = Clients.resource("http://localhost:8080/test/on")
                .parameters(params)
                .get();
    }

}
