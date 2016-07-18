package org.cheetah.fighter.worker;

import com.lmax.disruptor.EventHandler;
import org.cheetah.commons.logger.Loggers;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.AbstractWorker;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorWorker extends AbstractWorker implements EventHandler<DisruptorEvent> {
    private Handler handler;
    private List<Interceptor> interceptors;

    public DisruptorWorker(Handler handler, List<Interceptor> interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

    @Override
    public void onEvent(DisruptorEvent disruptorEvent, long sequence, boolean endOfBatch) throws Exception {
        Command command = disruptorEvent.get();
        long start = System.currentTimeMillis();
        work(command);
        Loggers.me().debugEnabled(this.getClass(), "work消耗了{}毫秒", System.currentTimeMillis() - start);
    }

    @Override
    public void work(Command command) {
        boolean success = invoke(command);
        if(success)
            handler.onSuccess(command);
        else handler.onFailure(command, null);
    }

    @Override
    protected boolean doWork(Command command) {
        return handler.handle(command);
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

}
