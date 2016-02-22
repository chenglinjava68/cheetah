package cheetah.distributor;

import cheetah.distributor.core.Configuration;
import cheetah.distributor.core.DispatcherWorker;
import cheetah.distributor.core.support.ApplicationEventEmitter;
import cheetah.distributor.event.ApplicationEvent;
import cheetah.distributor.event.ApplicationListener;
import cheetah.distributor.event.DomainEventListener;
import cheetah.distributor.event.SmartApplicationListener;
import cheetah.distributor.machine.HandlerTyped;
import cheetah.logger.Debug;
import cheetah.util.ArithUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/2.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HandlerTest {

    @Test
    public void log() {
        Debug.log(DispatcherWorker.class, "a");
    }

    @Test
    public void handlers() {
        HandlerTyped typed = HandlerTyped.Manager.convertFrom(DomainEventListener.class);
        System.out.println(typed);
    }

    @Test
    public void allot() {
        DispatcherWorker distributor = new DispatcherWorker();
        Configuration configuration = new Configuration();
        ArrayList listeners = new ArrayList();
        listeners.add(new SmartApplicationListenerTest());
        listeners.add(new ApplicationListenerTest());

        configuration.setEventListeners(listeners);
        distributor.setConfiguration(configuration);
        distributor.start();

    }

    static final AtomicLong atomicLong = new AtomicLong();

    @Test
    public void allot2() throws InterruptedException {
        DispatcherWorker distributor = new DispatcherWorker();
        Configuration configuration = new Configuration();

        ArrayList listeners = new ArrayList();
        listeners.add(new SmartApplicationListenerTest());
        listeners.add(new ApplicationListenerTest());

        configuration.setEventListeners(listeners);
        distributor.setConfiguration(configuration);
        distributor.start();

        while (true) {
            while (Thread.activeCount() < 1200) {
                new Thread(() -> {
                    while (true) {
                        ApplicationEventEmitter.launch(
                                new ApplicationEventTest("213")
                        );
                    }
                }).start();
            }
        }
    }


    @Test
    public void launch() throws InterruptedException {
//        CountDownLatch count = new CountDownLatch(1);
//        int i = 0;
//        while (true) {
//            i++;
//            if(i > 200000000)
//                break;
//            while (Thread.activeCount() < 100) {
//                new Thread(() -> {
//                    while (true) {
//                        ApplicationEventEmitter.launch(
//                                new ApplicationEventTest("213")
//                        );
//                    }
//                }).start();
//            }
//        }
        while (true) {
            ApplicationEventEmitter.launch(
                    new ApplicationEventTest("213")
            );
        }
//        count.await();
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
//            throw new RuntimeException();
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
            System.out.println("SmartApplicationListenerTest -- "+atomicLong.incrementAndGet());
        }
    }
}
