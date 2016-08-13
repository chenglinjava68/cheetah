package org.cheetah.bootstrap;

import org.cheetah.bootstrap.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Max on 2016/8/13.
 */
@ContextConfiguration("classpath:META-INF/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DubboDemoserviceTest {
    @Autowired
    private DemoService demoService;

    @Test
    public void get() {
        while (true) {
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        System.out.println(demoService.get());
        }
    }
}
