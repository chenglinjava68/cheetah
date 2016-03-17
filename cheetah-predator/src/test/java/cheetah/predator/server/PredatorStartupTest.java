package cheetah.predator.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Max on 2016/3/17.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PredatorStartupTest {

    @Test
    public void start() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        latch.await();
    }
}
