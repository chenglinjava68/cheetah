package org.cheetah.fighter.sample;

import org.cheetah.fighter.EventResult;
import org.cheetah.fighter.api.DomainEventPublisher;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Max on 2016/7/29.
 */
public class DomainEventPublisherTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/application.xml");
        SpringBeanFactoryProvider provider = new SpringBeanFactoryProvider(context);
        BeanFactory.setBeanFactoryProvider(provider);

        performance();
    }

    /**
     * –‘ƒ‹≤‚ ‘
     */
    public static void performance() {
        try {
            Thread[] threads = new Thread[10];
            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(() -> {
                    while (true) {
                        EventResult result = DomainEventPublisher.publish(
                                new DomainEventTest("huahng"), false
                        );
                        DomainEventPublisher.publish(
                                new DomainEventTest2("huahng"), false
                        );
                        //                    System.out.println(atomicLong2.incrementAndGet());
                    }
                });
                threads[i].start();
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
