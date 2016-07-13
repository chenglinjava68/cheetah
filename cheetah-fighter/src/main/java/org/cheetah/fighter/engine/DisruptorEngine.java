package org.cheetah.fighter.engine;

import com.lmax.disruptor.dsl.Disruptor;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.core.eventbus.AbstractEngine;
import org.cheetah.fighter.core.governor.Governor;
import org.cheetah.fighter.governor.DisruptorGovernor;
import org.cheetah.fighter.governor.DisruptorGovernorAdapter;

import java.util.Objects;

/**
 * Created by Max on 2016/2/29.
 */
public class DisruptorEngine extends AbstractEngine {

    @Override
    public Governor assignGovernor() {
        if (Objects.isNull(governor())) {
            Governor governor = governorFactory().createGovernor();
            governor = new DisruptorGovernorAdapter((DisruptorGovernor) governor, pluginChain());
            ((DisruptorGovernorAdapter) governor)
                    .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
            governor.registerHandlerSquad(context().handlers());
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
    }
}
