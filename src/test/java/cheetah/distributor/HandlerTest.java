package cheetah.distributor;

import cheetah.distributor.handler.HandlerTyped;
import cheetah.event.*;
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

        distributor.allot(new EventMessage(new ApplicationEventTest("213"), Collector.STATE_CALL_BACK));
    }

    static final AtomicLong atomicLong = new AtomicLong();

    @Test
    public void allot2() throws InterruptedException {
        Distributor distributor = new Distributor();
        Configuration configuration = new Configuration();
        Regulator regulator = new Regulator(distributor);
        ApplicationEventCollector collector = new ApplicationEventCollector(regulator);
        distributor.registrationCollector(ApplicationEvent.class, collector);

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
        while (true) {
            while (Thread.activeCount() < 1200) {
                new Thread(() -> {
                    while (true) {
                        ApplicationEventEmitter.launch(new ApplicationEventTest("213"), Collector.STATE);
                    }
                }).start();
            }
        }
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
            System.out.println("smart-> " + atomicLong.incrementAndGet());
        }
    }
}
