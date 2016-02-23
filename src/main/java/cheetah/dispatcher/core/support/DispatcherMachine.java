package cheetah.dispatcher.core.support;

import cheetah.dispatcher.core.AbstractDispatcher;
import cheetah.dispatcher.core.EventMessage;
import cheetah.dispatcher.core.EventResult;
import cheetah.dispatcher.core.NoMapperException;
import cheetah.dispatcher.event.Event;
import cheetah.dispatcher.governor.Governor;
import cheetah.dispatcher.machine.Feedback;
import cheetah.dispatcher.machine.Machine;
import cheetah.dispatcher.mapper.Mapper;
import cheetah.logger.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/1/29.
 */
public class DispatcherMachine extends AbstractDispatcher {

    @Override
    public EventResult dispatch(EventMessage eventMessage) {
        long start = System.currentTimeMillis();
        Event event = eventMessage.getEvent();
        Mapper.MachineMapperKey key = Mapper.MachineMapperKey.generate(event.getClass(), event.getSource().getClass());
        List<Machine> machines = this.getMapper().getMachine(key);
        if (!machines.isEmpty()) {
            Governor governor = getEngine().assignGovernor();
            Feedback report = governor.initialize()
                    .setEvent(event)
                    .registerMachineSquad(new ArrayList<>(machines))
                    .setFisrtSucceed(eventMessage.isFisrtWin())
                    .setNeedResult(eventMessage.isNeedResult())
                    .on()
                    .command();
            Debug.log(this.getClass(), "调度消耗的毫秒数 : " + (System.currentTimeMillis() - start));
            return new EventResult(event.getSource(), report.isFail());
        }
        throw new NoMapperException();
    }

}
