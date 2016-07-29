package org.cheetah.fighter.engine.support;

import com.lmax.disruptor.dsl.Disruptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.engine.AbstractEngine;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEngine extends AbstractEngine<Disruptor<DisruptorEvent>> {

    @Override
    public Disruptor<DisruptorEvent> getAsynchronous() {
        return (Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous();
    }

    /*@Override
    public RingBuffer getAsynchronous() {
        if (Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            governor = new DisruptorGovernorAdapter((DisruptorGovernor) governor, pluginChain());
            ((DisruptorGovernorAdapter) governor)
                    .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
            governor.registerHandlerSquad(context().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                ((DisruptorGovernorAdapter) clone)
                        .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((DisruptorGovernorAdapter) governor)
                        .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
                return governor;
            }
        }
    }*/
}
