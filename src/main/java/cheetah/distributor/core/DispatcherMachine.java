package cheetah.distributor.core;

import cheetah.distributor.event.Event;
import cheetah.distributor.governor.Governor;
import cheetah.distributor.machine.Feedback;
import cheetah.distributor.machine.Machine;
import cheetah.distributor.mapper.Mapper;
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
            Debug.log(this.getClass(), "调度花费的毫秒数 : " + (System.currentTimeMillis() - start));
            return new EventResult(event.getSource(), report.isFail());
        }
        throw new NoMapperException();
    }

}
