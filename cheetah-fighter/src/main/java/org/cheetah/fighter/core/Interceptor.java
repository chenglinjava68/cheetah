package org.cheetah.fighter.core;

import org.cheetah.fighter.event.Event;
import org.cheetah.fighter.worker.Command;

/**
 * @author Max
 */
public interface Interceptor {

    boolean preHandle(Command command) throws Exception;

    void postHandle(Command command) throws Exception;

    void afterCompletion(Command command, Exception ex) throws Exception;

    boolean supportsType(Class<? extends Event> event);
}
