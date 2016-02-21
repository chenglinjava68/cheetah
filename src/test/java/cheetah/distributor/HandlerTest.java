package cheetah.distributor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cheetah.distributor.core.*;
import cheetah.distributor.event.*;
import cheetah.distributor.machinery.actor.ActorWatcher;
import cheetah.distributor.machinery.Instruction;
import cheetah.distributor.worker.Order;
import cheetah.distributor.worker.HandlerTyped;
import cheetah.logger.Debug;
import cheetah.util.ArithUtil;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/2.
 */
//@ContextConfiguration("classpath:META-INF/application.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
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
        Regulator regulator = new Regulator(distributor);

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
        CountDownLatch count = new CountDownLatch(1);
        ActorSystem system = ActorSystem.create("system", ConfigFactory.load("actor.conf"));
        ActorRef actor = system.actorOf(Props.create(ActorWatcher.class), "governor");

        Event event = new ApplicationEventTest("123") {
        };
        Order message = new Order(event);
        List<ApplicationListener> listenerList = new ArrayList<>();
        listenerList.add(new SmartApplicationListenerTest());
        listenerList.add(new ApplicationListenerTest());

        Instruction task = new Instruction(event, listenerList);

        actor.tell(task, ActorRef.noSender());
        count.await();
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
