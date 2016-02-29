package cheetah.governor.support;

import cheetah.core.support.ApplicationEventEmitter;
import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.SmartApplicationListener;
import cheetah.util.ArithUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/24.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AkkaGovernorTest {

    public static final AtomicLong atomicLong = new AtomicLong();

    @Test
    public void launch2() throws InterruptedException {

        while (true) {
            ApplicationEventEmitter.launch(
                    new ApplicationEventTest("213")
            );
        }

    }


    @Test
    public void launch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                while (true) {
                    ApplicationEventEmitter.launch(
                            new ApplicationEventTest("213")
                    );
                }
            }).start();
        }
        latch.await();
    }

    public static class ApplicationEventTest extends ApplicationEvent {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public ApplicationEventTest(Object source) {
            super(source);
        }
    }

    public static class ApplicationListenerTest implements ApplicationListener<ApplicationEventTest> {
        @Override
        public void onApplicationEvent(ApplicationEventTest event) {
            double v = ArithUtil.round(Math.random() * 100, 0);
            long i = ArithUtil.convertsToLong(v);
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicLong.incrementAndGet());
        }
    }

    public static class SmartApplicationListenerTest implements SmartApplicationListener<ApplicationEventTest> {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
            return ApplicationEventTest.class == eventType;
        }

        @Override
        public boolean supportsSourceType(Class<?> sourceType) {
            return String.class == sourceType;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void onApplicationEvent(ApplicationEventTest event) {
            System.out.println("SmartApplicationListenerTest -- " + atomicLong.incrementAndGet());
        }
    }
}