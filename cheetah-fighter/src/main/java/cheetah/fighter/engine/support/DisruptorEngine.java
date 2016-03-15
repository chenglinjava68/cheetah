package cheetah.fighter.engine.support;

import cheetah.fighter.async.disruptor.DisruptorEvent;
import cheetah.fighter.engine.AbstractEngine;
import cheetah.fighter.governor.Governor;
import cheetah.fighter.governor.support.DisruptorGovernor;
import cheetah.fighter.governor.support.DisruptorGovernorAdapter;
import com.lmax.disruptor.dsl.Disruptor;

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
