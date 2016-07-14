package org.cheetah.fighter.governor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import org.cheetah.commons.logger.Info;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.core.governor.AbstractGovernor;
import org.cheetah.fighter.core.Feedback;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;
import java.util.Set;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorGovernor extends AbstractGovernor {
    private RingBuffer<DisruptorEvent> ringBuffer;

    @Override
    protected Feedback notifyAllWorker() {
        Info.log(this.getClass(), "notify {} worker", handlerMap().size());
        if (handlerMap().isEmpty())
            return Feedback.EMPTY;
        Translator translator = new Translator();
        Set<Class<? extends EventListener>> keys = this.handlerMap().keySet();
        for (Class<? extends EventListener> c : keys) {
            Command command = Command.of(details().event(), c);
            ringBuffer.publishEvent(translator, command);
        }

        return Feedback.SUCCESS;
    }

    public DisruptorGovernor setRingBuffer(RingBuffer<DisruptorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
        return this;
    }

    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Command> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Command data) {
            event.set(data);
        }
    }
}
