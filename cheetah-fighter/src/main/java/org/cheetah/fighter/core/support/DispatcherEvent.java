package org.cheetah.fighter.core.support;

import org.cheetah.fighter.core.AbstractDispatcher;
import org.cheetah.fighter.core.EventMessage;
import org.cheetah.fighter.core.EventResult;
import org.cheetah.fighter.core.NoMapperException;
import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.core.handler.Feedback;
import org.cheetah.fighter.core.handler.Handler;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/1/29.
 */
public class DispatcherEvent extends AbstractDispatcher {

    @Override
    public EventResult dispatch() {
        EventMessage eventMessage = context().eventMessage();
        Map<Class<? extends EventListener>, Handler> handlerMap = context().handlers();
        if (!handlerMap.isEmpty()) {
            Governor governor = engine().assignGovernor();
            Feedback report = governor.initialize()
                    .accept(eventMessage)
                    .registerHandlerSquad(handlerMap)
                    .command();
            return new EventResult(eventMessage.event().getSource(), report.isFail());
        }
        throw new NoMapperException();
    }

}
