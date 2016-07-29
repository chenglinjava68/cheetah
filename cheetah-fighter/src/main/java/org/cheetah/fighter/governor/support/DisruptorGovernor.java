package org.cheetah.fighter.governor.support;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.governor.AbstractGovernor;
import org.cheetah.fighter.worker.Command;

/**
 * Created by Max on 2016/2/29.
 */
@Deprecated
public class DisruptorGovernor extends AbstractGovernor {
    private RingBuffer<DisruptorEvent> ringBuffer;

    @Override
    protected Feedback notifyAllWorker() {
        long start = System.currentTimeMillis();
        if (handlers().isEmpty())
            return Feedback.EMPTY;
        Translator translator = new Translator();
        Command command = Command.of(details().event(), false);
        ringBuffer.publishEvent(translator, command);
        Loggers.me().debugEnabled(this.getClass(), "消耗了{}毫秒", System.currentTimeMillis() - start);
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
