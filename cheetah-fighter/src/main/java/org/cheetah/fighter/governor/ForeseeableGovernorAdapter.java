package org.cheetah.fighter.governor;

import org.cheetah.fighter.core.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.plugin.PluginChain;

/**
 * Created by Max on 2016/3/7.
 */
public class ForeseeableGovernorAdapter extends AbstractGovernorAdapter {

    public ForeseeableGovernorAdapter(ForeseeableGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorker(Worker worker) {
        ((ForeseeableGovernor) adaptee()).setWorker(worker);
    }
}
