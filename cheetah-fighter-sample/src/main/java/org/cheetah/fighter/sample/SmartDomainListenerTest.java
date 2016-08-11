package org.cheetah.fighter.sample;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.SmartDomainEventListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.cheetah.fighter.sample.DomainEventPublisherTest.requests;

/**
 * Created by maxhuang on 2016/7/18.
 */
@Component
public class SmartDomainListenerTest implements SmartDomainEventListener {
    public AtomicLong atomicLong1 = new AtomicLong();
    public AtomicLong atomicLong2 = new AtomicLong();
    @Override
    public boolean supportsEventType(Class<? extends DomainEvent> eventType) {
        return eventType == DomainEventTest.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return String.class == sourceType;
    }

    @Override
    public void onDomainEvent(DomainEvent event) {
        Random ramdon = new Random();
//        LockSupport.parkNanos(ramdon.nextInt(1000000));
//        try {
//            Thread.sleep(ramdon.nextInt(100));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        long strat = System.currentTimeMillis();
        long i = 10000000L;
        while (i > 0) {
            i--;
        }
        System.out.println(System.currentTimeMillis() - strat);

        requests.mark();
//        System.out.println("SmartDomainListenerTest -- " + atomicLong1.incrementAndGet() + "------" + atomicLong2.get());
//        throw new RuntimeException();
    }

    @Override
    public void onFinish(DomainEvent domainEvent) {
    }

    @Override
    public void onCancelled(DomainEvent domainEvent, Throwable e) {
        atomicLong2.incrementAndGet();
    }

}
