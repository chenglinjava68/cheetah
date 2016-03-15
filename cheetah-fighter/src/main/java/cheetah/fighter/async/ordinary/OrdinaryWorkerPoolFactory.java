package cheetah.fighter.async.ordinary;

import cheetah.fighter.async.AsynchronousFactory;
import cheetah.fighter.async.AsynchronousPoolFactory;
import cheetah.fighter.core.EventContext;
import cheetah.fighter.core.NoMapperException;
import cheetah.fighter.mapping.HandlerMapping;
import cheetah.fighter.worker.support.OrdinaryWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Max on 2016/2/29.
 */
public class OrdinaryWorkerPoolFactory implements AsynchronousPoolFactory<OrdinaryWorker> {
    private AsynchronousFactory<OrdinaryWorker> asynchronousFactory;
    private final Map<HandlerMapping.HandlerMapperKey, OrdinaryWorker> workerPool;
    private EventContext context;

    public OrdinaryWorkerPoolFactory() {
        this.workerPool = new HashMap<>();
    }

    public OrdinaryWorker createWorker() {
        OrdinaryWorker worker = this.workerPool.get(HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event()));
        if(Objects.nonNull(worker))
            return worker;
        return  this.asynchronousFactory.createAsynchronous(context.eventMessage().event().getClass().getName(),
                context.handlers(), context.interceptors());
    }

    @Override
    public OrdinaryWorker getAsynchronous() {
        OrdinaryWorker worker = this.workerPool.get(HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event()));
        if (Objects.nonNull(worker)) {
            return worker;
        } else {
            synchronized (this) {
                if(context.handlers().isEmpty())
                    throw new NoMapperException();
                worker = createWorker();
                HandlerMapping.HandlerMapperKey key = HandlerMapping.HandlerMapperKey.generate(context.eventMessage().event());
                this.workerPool.putIfAbsent(key, worker);
                return worker;
            }
        }
    }


    @Override
    public void setEventContext(EventContext context) {
        this.context = context;
    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {
        this.asynchronousFactory = asynchronousFactory;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        this.workerPool.clear();
        this.asynchronousFactory.stop();
    }

}
