package org.cheetah.fighter.sample;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.cheetah.fighter.DomainEventPublisher;
import org.cheetah.fighter.EventResult;
import org.cheetah.ioc.BeanFactory;
import org.cheetah.ioc.spring.SpringBeanFactoryProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by Max on 2016/7/29.
 */
public class DomainEventPublisherTest {
    static final AtomicLong atomicLong = new AtomicLong();
    public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    /**
     * 在控制台上打印输出
     */
    public static ConsoleReporter reporter = ConsoleReporter.forRegistry(METRIC_REGISTRY).build();


    /**
     * 实例化一个Meter
     */
    public static final Meter requests = METRIC_REGISTRY.meter(name(DomainEventPublisherTest.class, "request"));


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
        reporter.start(1, TimeUnit.SECONDS);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while (true) {
                    DomainEventPublisher.publish(
                            new DomainEventTest("huahng")
                    );

                }
            }).start();
        }


    }

    /**
     *
     */
    public static void publish() {

        EventResult result = DomainEventPublisher.publish(
                new DomainEventTest("huahng"), true, 1, TimeUnit.SECONDS
        );
//        EventResult result2 = DomainEventPublisher.publish(
//                new DomainEventTest2("huahng"), true
//        );
        System.out.println(result);
//        System.out.println(result2);
    }
}
