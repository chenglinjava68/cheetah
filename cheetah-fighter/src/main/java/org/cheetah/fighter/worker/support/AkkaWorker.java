package org.cheetah.fighter.worker.support;

import org.cheetah.common.logger.Err;
import org.cheetah.common.utils.Assert;
import org.cheetah.fighter.Interceptor;
import org.cheetah.fighter.handler.Handler;
import org.cheetah.fighter.worker.AbstractWorker;
import org.cheetah.fighter.worker.Command;

import java.util.List;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker extends AbstractWorker {

    public AkkaWorker(Handler handler, List<Interceptor> interceptors) {
        super(handler, interceptors);
    }

    @Override
    protected boolean doWork(Command command) {
        try {
            Assert.notNull(command, "order must not be null");
            handler.handle(command);
            return true;
        } catch (Exception e) {
            Err.log(this.getClass(), "AkkaWorker work fail.", e);
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
