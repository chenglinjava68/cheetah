package org.cheetah.bootstrap.controller;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/7/25.
 */
@Component
public class SpringListener implements SmartApplicationListener {
    private AtomicLong atomicLong = new AtomicLong();
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return SpringEvent.class == aClass;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return String.class == aClass;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        int i = 1000000;
        while (i > 0) {
            i--;
        }
        System.out.println(atomicLong.incrementAndGet());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
