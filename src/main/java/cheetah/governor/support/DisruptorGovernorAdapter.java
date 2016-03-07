package cheetah.governor.support;

import cheetah.core.async.disruptor.DisruptorEvent;
import cheetah.core.plugin.PluginChain;
import cheetah.governor.AbstractGovernorAdapter;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by Max on 2016/3/7.
 */
public class DisruptorGovernorAdapter extends AbstractGovernorAdapter {
    public DisruptorGovernorAdapter(DisruptorGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setRingBuffer(RingBuffer<DisruptorEvent> ringBuffer) {
        ((DisruptorGovernor) getAdaptee()).setRingBuffer(ringBuffer);
    }
}
