package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.domain.Entity;
import org.cheetah.domain.UUIDKeyEntity;
import org.cheetah.fighter.event.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/24.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventPublisherTest {

    public static final AtomicLong atomicLong = new AtomicLong();
    public static final AtomicLong atomicLong2 = new AtomicLong();
    public static final AtomicLong atomicLong3 = new AtomicLong();


    @Test
    public void launch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ApplicationEventPublisher.publish(
                                new ApplicationEventTest("213")
                        );
//                    listenerTest.onApplicationEvent(event);
                    }
                }
            }).start();

        }
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        DomainEvenPublisher.publish(
                                new DomainEventTest(new User("huahng"))
                        );
//                    listenerTest.onApplicationEvent(event);
                    }
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ApplicationEventPublisher.publish(
                                new ApplicationEventTest2("123")
                        );
//                    listenerTest.onApplicationEvent(event);
                    }
                }
            }).start();
        }
        latch.await();
    }


    @Test
    public void launch2() throws InterruptedException {
        while (true) {
            Thread.sleep(1);
            FighterContext.publish(
                    new ApplicationEventTest2("213")
            );
            FighterContext.publish(
                    new ApplicationEventTest("213")
            );
            FighterContext.publish(
                    new DomainEventTest(new User("hzf"))
            );
//            listenerTest.onApplicationEvent(event);
        }
    }


    @Test
    public void launch3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        int i = 0;
        while (true) {
            i++;
            ApplicationEventPublisher.publish(
                    new ApplicationEventTest("123")
            );
            if (i == 1000000) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
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

    public static class ApplicationEventTest2 extends ApplicationEvent {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public ApplicationEventTest2(Object source) {
            super(source);
        }
    }

    public static class DomainEventTest extends DomainEvent {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public DomainEventTest(Entity source) {
            super(source);
        }


    }

    public static class ApplicationListenerTest implements ApplicationListener<ApplicationEventTest> {
        @Override
        public void onApplicationEvent(ApplicationEventTest event) {
//            double v = ArithUtils.round(Math.random() * 100, 0);
//            long i = ArithUtils.convertsToLong(v);
//            try {
//                Thread.sleep(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("ApplicationListenerTest----" + atomicLong.incrementAndGet());
//            System.out.println(System.currentTimeMillis());
        }

        @Override
        public void onFinish() {

        }
    }

    public static class SmartApplicationListenerTest implements SmartApplicationListener {
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
        public void onApplicationEvent(ApplicationEvent event) {
            double v = ArithUtils.round(Math.random() * 100, 0);
            long i = ArithUtils.convertsToLong(v);
//            try {
//                Thread.sleep(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            int k = 100000;
            while (k > 0) {
                k--;
            }
            System.out.println("SmartApplicationListenerTest -- " + atomicLong.incrementAndGet());
        }

        @Override
        public void onFinish() {

        }
    }

    public static class SmartApplicationListenerTest2 implements SmartApplicationListener {
        @Override
        public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
            return ApplicationEventTest2.class == eventType;
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
        public void onApplicationEvent(ApplicationEvent event) {
            double v = ArithUtils.round(Math.random() * 100, 0);
            long i = ArithUtils.convertsToLong(v);
//            try {
//                Thread.sleep(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            int k = 100000;
            while (k > 0) {
                k--;
            }
            System.out.println("SmartApplicationListenerTest2 -- " + atomicLong2.incrementAndGet());
        }

        @Override
        public void onFinish() {

        }
    }

    public static class SmartDomainListenerTest implements SmartDomainEventListener {

        @Override
        public boolean supportsEventType(Class<? extends DomainEvent> eventType) {
            return eventType == DomainEventTest.class;
        }

        @Override
        public boolean supportsSourceType(Class<? extends Entity> sourceType) {
            return User.class == sourceType;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void onDomainEvent(DomainEvent event) {
            double v = ArithUtils.round(Math.random() * 100, 0);
            long i = ArithUtils.convertsToLong(v);
//            try {
//                Thread.sleep(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            int k = 100000;
            while (k > 0) {
                k--;
            }
            System.out.println("DomainEventTest -- " + atomicLong3.incrementAndGet());
        }

        @Override
        public void onFinish() {

        }

    }

    public static class User extends UUIDKeyEntity {

        private static final long serialVersionUID = -2269393138381549806L;
        private String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    "} " + super.toString();
        }
    }

}