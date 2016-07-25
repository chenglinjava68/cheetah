package org.cheetah.fighter.api;

import com.google.common.collect.Lists;
import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.handler.support.DomainEventHandler;
import org.cheetah.fighter.worker.support.ForeseeableWorker;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/24.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventPublisherTest {

    public static final AtomicLong atomicLong2 = new AtomicLong();
    @Autowired
    SpringBeanFactoryProvider springBeanFactoryProvider;
    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void before() {
        BeanFactory.setBeanFactoryProvider(springBeanFactoryProvider);
    }

    @Test
    public void test() {
        ForeseeableWorker worker = new ForeseeableWorker( );
        worker.setExecutor(Executors.newFixedThreadPool(64));
        while (true) {
        worker.work(Command.of(new DomainEventTest2(new User("user")), false));
        }
    }

    @Test
    public void launch() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                while (true) {
                    DomainEventPublisher.publish(
                            new DomainEventTest2(new User("huahng"))
                    );
//                    System.out.println(atomicLong2.incrementAndGet());
                }
            });
            threads[i].start();
            threads[i].join();
        }
    }

    @Test
    public void springEventTest() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                while (true) {
                    applicationContext.publishEvent(new SpringEvent("a"));

                }
            });
            threads[i].start();
            threads[i].join();
        }
    }


    @Test
    public void launch2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        FighterContext.publish(
                new DomainEventTest2(new User("hzf"))
        ); FighterContext.publish(
                new DomainEventTest2(new User("hzf"))
        ); FighterContext.publish(
                new DomainEventTest2(new User("hzf"))
        ); FighterContext.publish(
                new DomainEventTest2(new User("hzf"))
        );

        latch.await();
    }


    @Test
    public void launch3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        int i = 0;
        while (true) {
            i++;
            DomainEventPublisher.publish(
                    new ApplicationEventTest(new User("hzf"))
            );
            if (i == 1000000) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
        latch.await();
    }

    public static class ApplicationEventTest extends DomainEvent {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public ApplicationEventTest(User source) {
            super(source);
        }
    }

    public static class ApplicationEventTest2 extends DomainEvent {

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

    public static class DomainEventTest extends DomainEvent<User> {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public DomainEventTest(User source) {
            super(source);
        }


    }

    public static class DomainEventTest2 extends DomainEvent<User> {

        /**
         * Constructs a prototypical Event.
         *
         * @param source The object on which the Event initially occurred.
         * @throws IllegalArgumentException if source is null.
         */
        public DomainEventTest2(User source) {
            super(source);
        }


    }


    public static class User {

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