package org.cheetah.fighter.sample;

import org.cheetah.fighter.DomainEvent;
import org.cheetah.fighter.SmartDomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maxhuang on 2016/7/18.
 */
public class SmartDomainListenerTest2 implements SmartDomainEventListener {
    public AtomicLong atomicLong1 = new AtomicLong();
    public AtomicLong atomicLong2 = new AtomicLong();
    @Override
    public boolean supportsEventType(Class<? extends DomainEvent> eventType) {
        return eventType == DomainEventTest2.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return String.class == sourceType;
    }

    @Override
    public void onDomainEvent(DomainEvent event) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("SmartDomainListenerTest2 -- " + atomicLong1.incrementAndGet() + "------" + atomicLong2.get());
//        throw new RuntimeException();
    }

    @Override
    public void onFinish(DomainEvent domainEvent) {
        System.out.println("on finish");
    }

    @Override
    public void onCancelled(DomainEvent domainEvent, Throwable e) {
        System.out.println("on cancelled");
        atomicLong2.incrementAndGet();
    }

}