package org.cheetah.bootstrap.controller;


import org.cheetah.fighter.api.DomainEventListener;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/3/1.
 */
public class ApplicationListenerTest implements DomainEventListener<ApplicationEventTest> {
    private AtomicLong atomicLong = new AtomicLong();
    private AtomicLong atomicLong2 = new AtomicLong();
    @Override
    public void onDomainEvent(ApplicationEventTest applicationEventTest) {
        int i = 1000000;
        while (i > 0) {
            i--;
        }
    }

    @Override
    public void onFinish(ApplicationEventTest applicationEventTest) {
        System.out.println(atomicLong.incrementAndGet());
    }

    @Override
    public void onCancelled(ApplicationEventTest applicationEventTest, Throwable throwable) {
        System.out.println("error-----------"+atomicLong2.incrementAndGet());
    }
}
