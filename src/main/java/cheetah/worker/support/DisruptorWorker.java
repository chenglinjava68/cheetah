package cheetah.worker.support;

import cheetah.core.async.disruptor.DisruptorEvent;
import cheetah.handler.Directive;
import cheetah.handler.Handler;
import cheetah.worker.Command;
import cheetah.worker.Worker;
import com.lmax.disruptor.EventHandler;

import java.util.EventListener;
import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker implements Worker, EventHandler<DisruptorEvent> {
    private Map<Class<? extends EventListener>, Handler> handlerMap;

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        Command command = disruptorEvent.get();
        work(command);
    }

    @Override
    public void work(Command command) {
        handlerMap.get(command.eventListener()).send(new Directive(command.event(), true));
    }

    public DisruptorWorker setHandlerMap(Map<Class<? extends EventListener>, Handler> handlerMap) {
        this.handlerMap = handlerMap;
        return this;
    }
}
