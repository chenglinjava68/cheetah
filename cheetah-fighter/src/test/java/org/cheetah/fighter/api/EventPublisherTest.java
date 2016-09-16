package org.cheetah.fighter.api;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.EventResult;
import org.cheetah.fighter.FighterContext;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void launch2() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
        EventResult result = FighterContext.publish(
                new DomainEventTest2(new User("hzf")), true
        );
        System.out.println(result);
        Thread.sleep(1000);
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