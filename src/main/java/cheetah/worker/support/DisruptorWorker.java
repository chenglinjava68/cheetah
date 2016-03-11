package cheetah.worker.support;

import cheetah.async.disruptor.DisruptorEvent;
import cheetah.core.Interceptor;
import cheetah.handler.Directive;
import cheetah.handler.Handler;
import cheetah.worker.Command;
import cheetah.worker.Worker;
import com.lmax.disruptor.EventHandler;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker implements Worker, EventHandler<DisruptorEvent> {
    private Map<Class<? extends EventListener>, Handler> handlerMap;
    private List<Interceptor> interceptors;

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        Command command = disruptorEvent.get();
        work(command);
    }

    @Override
    public void doWork(Command command) {
        try {
            handlerMap.get(command.eventListener()).handle(new Directive(command.event(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setHandlerMap(Map<Class<? extends EventListener>, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    Map<Class<? extends EventListener>, Handler> getHandlerMap() {
        return handlerMap;
    }

}
