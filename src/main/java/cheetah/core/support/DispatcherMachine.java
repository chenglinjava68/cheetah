package cheetah.core.support;

import cheetah.core.AbstractDispatcher;
import cheetah.core.EventMessage;
import cheetah.core.EventResult;
import cheetah.core.NoMapperException;
import cheetah.event.Event;
import cheetah.governor.Governor;
import cheetah.machine.Feedback;
import cheetah.machine.Machine;

import java.util.*;

/**
 * Created by Max on 2016/1/29.
 */
public class DispatcherMachine extends AbstractDispatcher {

    @Override
    public EventResult dispatch(EventMessage eventMessage, Map<Class<? extends EventListener>, Machine> machines) {
        Event event = eventMessage.event();
        if (!machines.isEmpty()) {
            Governor governor = getEngine().assignGovernor();
            Feedback report = governor.initialize()
                    .setEvent(event)
                    .registerMachineSquad(new HashMap<>(machines))
                    .setFisrtSucceed(eventMessage.fisrtWin())
                    .setNeedResult(eventMessage.needResult())
                    .command();
            return new EventResult(event.getSource(), report.isFail());
        }
        throw new NoMapperException();
    }

}
