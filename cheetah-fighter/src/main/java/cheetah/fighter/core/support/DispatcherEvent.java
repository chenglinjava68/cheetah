package cheetah.fighter.core.support;

import cheetah.fighter.core.AbstractDispatcher;
import cheetah.fighter.core.EventMessage;
import cheetah.fighter.core.EventResult;
import cheetah.fighter.core.NoMapperException;
import cheetah.fighter.handler.Feedback;
import cheetah.fighter.handler.Handler;
import cheetah.fighter.governor.Governor;

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
                    .registerEvent(eventMessage.event())
                    .registerHandlerSquad(handlerMap)
                    .setFisrtSucceed(eventMessage.fisrtWin())
                    .setNeedResult(eventMessage.needResult())
                    .command();
            return new EventResult(eventMessage.event().getSource(), report.isFail());
        }
        throw new NoMapperException();
    }

}
