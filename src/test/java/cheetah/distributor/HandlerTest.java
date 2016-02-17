package cheetah.distributor;

import cheetah.distributor.handler.ApplicationEventHandler;
import cheetah.distributor.handler.Handler;
import cheetah.distributor.handler.HandlerTyped;
import cheetah.event.ApplicationEvent;
import cheetah.event.ApplicationListener;
import cheetah.event.DomainEventListener;
import cheetah.event.SmartApplicationListener;
import cheetah.logger.Debug;
import cheetah.util.ArithUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/2.
 */
public class HandlerTest {
    @Test
    public void log() {
        Debug.log(Distributor.class, "a");
    }

    @Test
    public void handlers() {
        HandlerTyped typed = HandlerTyped.Manager.convertFrom(DomainEventListener.class);
        System.out.println(typed);
    }

    @Test
    public void allot() {
        Distributor distributor = new Distributor();
        Configuration configuration = new Configuration();
        ArrayList listeners = new ArrayList();
        listeners.add(new SmartApplicationListenerTest());
        listeners.add(new ApplicationListenerTest());

        configuration.setEventListeners(listeners);
        distributor.setConfiguration(configuration);
        distributor.start();

        for (int i = 0; i < 3; i++)
            distributor.allot(new EventMessage(new ApplicationEventTest("213"), Handler.ProcessMode.STATE_CALL_BACK));
    }

    @Test
    public void applicatoinhandler() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicLong atomicLong = new AtomicLong();
        ExecutorService executorService = Executors.newCachedThreadPool();
        ApplicationEventHandler handler = new ApplicationEventHandler((ApplicationListener<ApplicationEventTest>) event -> {
//                        Debug.log(this.getClass(), event.occurredTime() + "");
//                        Debug.log(this.getClass(), event.getSource() + "");
            double v = ArithUtil.round(Math.random() * 100, 0);
            long i = ArithUtil.convertsToLong(v);
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicLong.incrementAndGet());
//            throw new RuntimeException();
        }, executorService);
        while (true) {
            if (atomicLong.get() == 1000000) break;
            while (Thread.activeCount() < 1000) {
                new Thread(() -> {
                    while (true) {
//                        handler.handle(new ApplicationEventTest("test"), true);
                        try {
                            handler.handle(new EventMessage(new ApplicationEventTest("test"), Handler.ProcessMode.JDK_UNIMPEDED), (eventMessage, exceptionObject, exceptionMessage) -> {
                                System.out.println(atomicLong.incrementAndGet());
                            });
                        } finally {
                            handler.removeFuture();
                        }
                    }
                }).start();
            }
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
            System.out.println("123");
        }
    }

    public static class SmartApplicationListenerTest implements SmartApplicationListener<ApplicationEventTest> {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
            return false;
        }

        @Override
        public boolean supportsSourceType(Class<?> sourceType) {
            return false;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void onApplicationEvent(ApplicationEventTest event) {
            System.out.println("smart");
        }
    }
}
