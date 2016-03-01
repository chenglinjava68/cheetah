package cheetah.async.disruptor;

import cheetah.async.AsynchronousFactory;
import cheetah.async.AsynchronousPoolFactory;
import cheetah.event.Event;
import cheetah.mapper.Mapper;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorPoolFactory implements AsynchronousPoolFactory<Disruptor<DisruptorEvent>> {
    private DisruptorFactory disruptorFactory;
    private Mapper mapper;
    private Map<Mapper.MachineMapperKey, Disruptor<DisruptorEvent>> machineDisruptors;

    public Disruptor<DisruptorEvent> createDisruptor() {
        return this.disruptorFactory.createDisruptor();
    }

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous(Event event) {
        return null;
    }

    @Override
    public void setMapper(Mapper mapper) {

    }

    @Override
    public void setAsynchronousFactory(AsynchronousFactory asynchronousFactory) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public DisruptorFactory disruptorFactory() {
        return disruptorFactory;
    }

    public void setDisruptorFactory(DisruptorFactory disruptorFactory) {
        this.disruptorFactory = disruptorFactory;
    }

}
