package cheetah.governor.support;

import cheetah.async.disruptor.DisruptorEvent;
import cheetah.governor.AbstractGovernor;
import cheetah.handler.Feedback;
import cheetah.worker.Command;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorGovernor extends AbstractGovernor {
    private RingBuffer<DisruptorEvent> ringBuffer;

    @Override
    protected Feedback notifyAllWorker() {
        if (handlerMap().isEmpty())
            return Feedback.EMPTY;
        Translator translator = new Translator();
        for (Class<? extends EventListener> clz : this.handlerMap().keySet()) {
            Command command = Command.of(event(), clz);
            ringBuffer.publishEvent(translator, command);
        }

//        if (!feedbackMap.isEmpty()) {
//            interceptorChain.pluginAll(feedbackMap);
//        }
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
