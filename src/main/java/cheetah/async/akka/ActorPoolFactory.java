package cheetah.async.akka;

import akka.actor.ActorRef;
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

    private ActorFactory actorFactory;
    private Mapper mapper; //事件映射的工作机器
    private ConcurrentHashMap<String, ActorRef> actorPool = new ConcurrentHashMap<>();

    public ActorPoolFactory() {
        this.actorFactory = new ActorFactory();
    }

    public ActorPoolFactory(ActorFactory actorFactory) {
        this.actorFactory = actorFactory;
    }

    public ActorRef createActor(Event event) {
        String name = event.getClass().getName();
        Map<Class<? extends EventListener>, Machine> machines = getMapperFrom(event);
        return this.actorFactory.createAsynchronous(name, machines);
    }

    @Override
    public ActorRef getAsynchronous(Event event) {
        String name = event.getClass().getName();
        if(!this.mapper.isExists(Mapper.MachineMapperKey.generate(event)))
            throw new NoMapperException();
        if(actorPool.containsKey(name))
            return this.actorPool.get(name);
        else {
            ActorRef actor = createActor(event);
            this.actorPool.put(name, actor);
            return actor;
        }
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
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
            actorFactory.actorSystem().stop(actor);
        }
        actorFactory.stop();
    }

}
