package org.cheetah.fighter.sample;

import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/7/29.
 */
public class DomainEventPublisherTest {
    static final AtomicLong atomicLong = new AtomicLong();
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/application.xml");
        SpringBeanFactoryProvider provider = new SpringBeanFactoryProvider(context);
        BeanFactory.setBeanFactoryProvider(provider);

        performance();
//        publish();
    }

    /**
     * 性能测试
     */
    public static void performance() {
        CountDownLatch latch = new CountDownLatch(1);
            Thread[] threads = new Thread[10];
            for (int i = 0; i < 20; i++) {
                threads[i] = new Thread(() -> {
                    while (true) {
                        org.cheetah.fighter.api.DomainEventPublisher.publish(
                                new DomainEventTest("huahng")
                        );
                        org.cheetah.fighter.api.DomainEventPublisher.publish(
                                new DomainEventTest2("huahng")
                        );
                    }
                });
                threads[i].start();
            }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public static void publish() {

        EventResult result = org.cheetah.fighter.api.DomainEventPublisher.publish(
                new DomainEventTest("huahng"), true, 1, TimeUnit.SECONDS
        );
//        EventResult result2 = DomainEventPublisher.publish(
//                new DomainEventTest2("huahng"), true
//        );
        System.out.println(result);
//        System.out.println(result2);
    }
}
