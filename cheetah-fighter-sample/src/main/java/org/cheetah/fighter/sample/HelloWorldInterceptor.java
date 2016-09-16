package org.cheetah.fighter.sample;

import org.cheetah.fighter.Event;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.worker.Command;

/**
 * Created by Max on 2016/3/8.
 */
//@Component
public class HelloWorldInterceptor implements Interceptor {
    @Override
    public boolean preHandle(Command command) throws Exception {
        System.out.println("prehandle");
        return true;
    }

    @Override
    public void postHandle(Command command) throws Exception {
        System.out.println("posthandle");
    }

    @Override
    public void afterCompletion(Command command, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }

    @Override
    public boolean supportsType(Class<? extends Event> event) {
        return DomainEventTest.class == event;
    }
}
