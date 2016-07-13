package org.cheetah.fighter.governor;

import org.cheetah.fighter.core.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.core.worker.Worker;
import org.cheetah.fighter.plugin.PluginChain;

/**
 * Created by Max on 2016/3/7.
 */
public class OrdinaryGovernorAdapter extends AbstractGovernorAdapter {

    public OrdinaryGovernorAdapter(OrdinaryGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorker(Worker worker) {
        ((OrdinaryGovernor) adaptee()).setWorker(worker);
    }
}
