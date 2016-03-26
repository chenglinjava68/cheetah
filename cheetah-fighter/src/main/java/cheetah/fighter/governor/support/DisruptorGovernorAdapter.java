package cheetah.fighter.governor.support;

import cheetah.fighter.async.disruptor.DisruptorEvent;
import cheetah.fighter.plugin.PluginChain;
import cheetah.fighter.governor.AbstractGovernorAdapter;
import com.lmax.disruptor.RingBuffer;

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
