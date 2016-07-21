package org.cheetah.fighter.governor.support;

import com.lmax.disruptor.RingBuffer;
import org.cheetah.fighter.async.disruptor.DisruptorEvent;
import org.cheetah.fighter.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.plugin.PluginChain;

/**
 * Created by Max on 2016/3/7.
 */
public class DisruptorGovernorAdapter extends AbstractGovernorAdapter {
    public DisruptorGovernorAdapter(DisruptorGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setRingBuffer(RingBuffer<DisruptorEvent> ringBuffer) {
        ((DisruptorGovernor) adaptee()).setRingBuffer(ringBuffer);
    }
}
