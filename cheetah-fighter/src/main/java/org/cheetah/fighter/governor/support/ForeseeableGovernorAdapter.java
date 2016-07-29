package org.cheetah.fighter.governor.support;

import org.cheetah.fighter.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.worker.Worker;
import org.cheetah.fighter.plugin.PluginChain;

/**
 * Created by Max on 2016/3/7.
 */
@Deprecated
public class ForeseeableGovernorAdapter extends AbstractGovernorAdapter {

    public ForeseeableGovernorAdapter(ForeseeableGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorkers(Worker[] workers) {
        ((ForeseeableGovernor) adaptee()).setWorkers(workers);
    }
}
