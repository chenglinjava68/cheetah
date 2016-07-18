package org.cheetah.fighter.api;

import org.cheetah.commons.utils.ArithUtils;
import org.cheetah.domain.UUIDKeyEntity;
import org.cheetah.fighter.core.event.DomainEvent;
import org.cheetah.fighter.core.event.DomainEventListener;
import org.cheetah.fighter.core.event.SmartDomainEventListener;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
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

    @Before
    public void before() {
        BeanFactory.setBeanFactoryProvider(springBeanFactoryProvider);
    }

    @Test
    public void launch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
//                    System.out.println(atomicLong2.incrementAndGet());
                    DomainEventPublisher.publish(
                            new DomainEventTest2(new User("huahng"))
                    );
                }
            }).start();
        }

        latch.await();
    }


    @Test
    public void launch2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        FighterContext.publish(
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