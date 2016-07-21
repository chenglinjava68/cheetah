package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.EventMessage;
import org.cheetah.fighter.Feedback;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.engine.support.EngineStrategy;
import org.cheetah.fighter.worker.Command;
import org.cheetah.fighter.worker.WorkerAdapter;

/**
 * Created by Max on 2016/7/21.
 */
public class DisruptorWorkerAdapter implements WorkerAdapter {
    private RingBuffer<DisruptorEvent> ringBuffer;

    @Override
    public boolean supports(Object object) {
        return object == EngineStrategy.DISRUPTOR;
    }

    @Override
    public Feedback work(EventMessage eventMessage) {
        long start = System.currentTimeMillis();
        Translator translator = new Translator();
        ringBuffer.publishEvent(translator, Command.of(eventMessage.event(), false));
        Loggers.me().debugEnabled(this.getClass(), "消耗了{}毫秒", System.currentTimeMillis() - start);
        return Feedback.SUCCESS;
    }

    public void setRingBuffer(RingBuffer<DisruptorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Command> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Command data) {
            event.set(data);
        }
    }
}
