package org.cheetah.commons.httpclient;

import org.cheetah.commons.httpclient.api.Clients;
import org.junit.Test;

/**
 * Created by maxhuang on 2016/7/5.
 */
public class HttpClientTest {

    @Test
    public void test() {
        Clients.resource("http://baidu.com").post();
    }


}
