package cheetah.engine.support;

import cheetah.async.disruptor.DisruptorEvent;
import cheetah.engine.AbstractEngine;
import cheetah.governor.Governor;
import cheetah.governor.support.DisruptorGovernor;
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
            ((DisruptorGovernor) governor)
                    .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
            governor.registerHandlerSquad(context().getHandlers());
            setGovernor(governor);
            return governor;
        } else {
            try {
                Governor clone = governor().kagebunsin();
                clone.reset();
                ((DisruptorGovernor) clone)
                        .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
                return clone;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Governor governor = governorFactory().createGovernor();
                ((DisruptorGovernor) governor)
                        .setRingBuffer(((Disruptor<DisruptorEvent>) asynchronousPoolFactory().getAsynchronous()).getRingBuffer());
                return governor;
            }
        }
    }
}
