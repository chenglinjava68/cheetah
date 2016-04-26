package org.cheetah.fighter.governor.support;

import org.cheetah.fighter.plugin.PluginChain;
import org.cheetah.fighter.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.worker.Worker;

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
