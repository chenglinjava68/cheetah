package cheetah.core;

import cheetah.event.Event;
import cheetah.worker.Command;

/**
 * @author Max
 */
public interface Interceptor {

    boolean preHandle(Command command) throws Exception;

    void postHandle(Command command) throws Exception;

    boolean supportsType(Class<? extends Event> event);
}
