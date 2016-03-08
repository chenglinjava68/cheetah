package cheetah.api;

import cheetah.core.Interceptor;
import cheetah.event.Event;
import cheetah.worker.Command;

/**
 * Created by Max on 2016/3/8.
 */
public class HelloWorldInterceptor implements Interceptor {
    @Override
    public boolean preHandle(Command command) throws Exception {
        System.out.println("prehandle");
        return false;
    }

    @Override
    public void postHandle(Command command) throws Exception {
        System.out.println("posthandle");
    }

    @Override
    public boolean supportsType(Class<? extends Event> event) {
        return EventPublisherTest.ApplicationEventTest.class == event;
    }
}
