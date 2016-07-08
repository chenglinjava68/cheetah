package org.cheetah.commons.httpclient;

import org.cheetah.commons.httpclient.api.Clients;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {
    AtomicLong atomicLong = new AtomicLong();
    AtomicLong erratomicLong = new AtomicLong();

    /**
     * 基于实体的post请求
     */
    @Test
    public void post1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        while (true) {
            while (Thread.activeCount() < 10) {
                executorService.submit(() -> {
                    while (true) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long start = System.currentTimeMillis();
                        try {
                            User user = Clients.resource("http://localhost:8080/test")
                                    .entity(new User("user", "pass"))
                                    .timeout(500)
                                    .post(User.class);
                        } catch (Exception e) {
                            erratomicLong.incrementAndGet();
                        }
                        System.out.println(System.currentTimeMillis() - start + "--------" + atomicLong.incrementAndGet()+"------------"+erratomicLong.get());
                    }

                });

            }
        }
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
