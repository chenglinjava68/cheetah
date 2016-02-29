package cheetah.async.disruptor;

import cheetah.mapper.Mapper;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Map;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorPoolFactory {
    private DisruptorFactory disruptorFactory;
    private Mapper mapper;
    private Map<Mapper.MachineMapperKey, Disruptor<DisruptorEvent>> machineDisruptors;

    public Disruptor<DisruptorEvent> createDisruptor() {
        return this.disruptorFactory.createDisruptor();
    }

    public DisruptorFactory disruptorFactory() {
        return disruptorFactory;
    }

    public void setDisruptorFactory(DisruptorFactory disruptorFactory) {
        this.disruptorFactory = disruptorFactory;
    }
}
