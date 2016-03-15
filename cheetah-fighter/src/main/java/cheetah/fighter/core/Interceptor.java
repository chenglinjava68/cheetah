package cheetah.fighter.core;

import cheetah.fighter.event.Event;
import cheetah.fighter.worker.Command;

/**
 * @author Max
 */
public interface Interceptor {

    boolean preHandle(Command command) throws Exception;

    void postHandle(Command command) throws Exception;

    boolean supportsType(Class<? extends Event> event);
}
