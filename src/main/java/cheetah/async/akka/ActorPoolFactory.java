package cheetah.async.akka;

import akka.actor.ActorRef;
import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.core.NoMapperException;
import cheetah.event.Event;
import cheetah.machine.Machine;
import cheetah.mapper.Mapper;

import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * actor池的工厂
 * Created by Max on 2016/2/29.
 */
public class ActorPoolFactory implements AsynchronousPoolFactory<ActorRef> {

    private AsynchronousFactory<ActorRef> actorFactory;
    private Mapper mapper; //事件映射器
    private ConcurrentHashMap<Mapper.MachineMapperKey, ActorRef> actorPool = new ConcurrentHashMap<>();

    public ActorPoolFactory() {
        this.actorFactory = new ActorFactory();
    }

    public ActorPoolFactory(ActorFactory actorFactory) {
        this.actorFactory = actorFactory;
    }

    public ActorRef createActor(Event event) {
        String name = event.getClass().getName();
        Mapper.MachineMapperKey mapperKey = Mapper.MachineMapperKey.generate(event);
        if (actorPool.containsKey(mapperKey))
            return this.actorPool.get(mapperKey);
        Map<Class<? extends EventListener>, Machine> machines = getMapperFrom(event);
        return this.actorFactory.createAsynchronous(name, machines);
    }

    @Override
    public ActorRef getAsynchronous(Event event) {
        Mapper.MachineMapperKey mapperKey = Mapper.MachineMapperKey.generate(event);
        if (actorPool.containsKey(mapperKey))
            return this.actorPool.get(mapperKey);
        else {
            synchronized (this) {
                if (!this.mapper.isExists(mapperKey))
                    throw new NoMapperException();
                ActorRef actor = createActor(event);
                this.actorPool.put(mapperKey, actor);
                return actor;
            }
        }
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {
        this.actorFactory = asynchronousFactory;
    }

    private Map<Class<? extends EventListener>, Machine> getMapperFrom(Event event) {
        Map<Class<? extends EventListener>, Machine> machines = mapper.getMachine(Mapper.MachineMapperKey.generate(event));
        if (machines.isEmpty())
            throw new NoMapperException();
        return machines;
    }

    @Override
    public void start() {
        actorFactory.start();
    }

    @Override
    public void stop() {
        for (ActorRef actor : this.actorPool.values()) {
            ((ActorFactory)actorFactory).actorSystem().stop(actor);
        }
        actorFactory.stop();
    }

}
