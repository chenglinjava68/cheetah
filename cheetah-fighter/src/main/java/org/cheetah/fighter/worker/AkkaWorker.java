package org.cheetah.fighter.worker;

import org.cheetah.commons.logger.Info;
import org.cheetah.commons.utils.Assert;
import org.cheetah.fighter.core.Interceptor;
import org.cheetah.fighter.core.handler.Directive;
import org.cheetah.fighter.core.handler.Handler;
import org.cheetah.fighter.core.worker.AbstractWorker;
import org.cheetah.fighter.core.worker.Command;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker extends AbstractWorker {
    private Map<Class<? extends EventListener>, Handler> eventlistenerMapper;
    private List<Interceptor> interceptors;

    public AkkaWorker(Map<Class<? extends EventListener>, Handler> eventlistenerMapper) {
        this.eventlistenerMapper = eventlistenerMapper;
    }

    public AkkaWorker() {

    }

    @Override
    public void doWork(Command command) {
        try {
            Assert.notNull(command, "order must not be null");
            Handler machine = eventlistenerMapper.get(command.eventListener());
            machine.handle(new Directive(command.event(), command.callback(), command.needResult()));
        } catch (Exception e) {
            Info.log(this.getClass(), "machine execute fail.", e);
        }
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return this.interceptors;
    }

}
