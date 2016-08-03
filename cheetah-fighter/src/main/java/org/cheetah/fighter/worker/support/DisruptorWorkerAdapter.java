package org.cheetah.fighter.worker.support;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
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
    private final RingBuffer<DisruptorEvent> ringBuffer;

    public DisruptorWorkerAdapter(RingBuffer<DisruptorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public boolean supports(Object object) {
        return object == EngineStrategy.DISRUPTOR;
    }

    @Override
    public Feedback work(EventMessage eventMessage) {
        Translator translator = new Translator();
        ringBuffer.publishEvent(translator, Command.of(eventMessage.event(), false));
        return Feedback.SUCCESS;
    }

    static class Translator implements EventTranslatorOneArg<DisruptorEvent, Command> {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Command data) {
            event.set(data);
        }
    }
}
