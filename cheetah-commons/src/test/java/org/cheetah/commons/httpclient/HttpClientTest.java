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
    /**
     * 基于实体的post请求
     */
    @Test
    public void post1() {
        Clients.resource("http://localhost:8080/test")
                .entity(new User("user", "pass"))
                .timeout(2000)
                .post();
    }

    /**
     * 基于表单
     */
    @Test
    public void post2() {
        Form form = Form.create().
                parameter("username", "user")
                .parameter("password", "pass");
        Clients.resource("http://localhost:8080/test/form")
                .form(form)
                .timeout(2000)
                .post();
    }

    /**
     * 基于get查询参数
     */
    @Test
    public void get() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", "user");
        params.put("pass", "pass");
        Clients.resource("http://localhost:8080/test/on")
                .parameters(params)
                .get();
    }

}
