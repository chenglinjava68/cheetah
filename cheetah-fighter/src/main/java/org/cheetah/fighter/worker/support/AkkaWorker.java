package org.cheetah.fighter.worker.support;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.commons.utils.Assert;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker extends AbstractWorker {
    private Map<Class<? extends EventListener>, Handler> eventlistenerMapper;
    private List<Interceptor> interceptors;

    public AkkaWorker(Handler handler, List<Interceptor> interceptors) {
        super(handler, interceptors);
    }


    @Override
    protected boolean doWork(Command command) {
        try {
            Assert.notNull(command, "order must not be null");
            Handler machine = eventlistenerMapper.get(null);
            return machine.handle(command);
        } catch (Exception e) {
            Loggers.me().error(this.getClass(), "AkkaWorker work fail.", e);
        }
        return false;
    }

    @Override
    public void work(Command command) {

    }

    @Override
    public List<Interceptor> getInterceptors() {
        return this.interceptors;
    }

}
