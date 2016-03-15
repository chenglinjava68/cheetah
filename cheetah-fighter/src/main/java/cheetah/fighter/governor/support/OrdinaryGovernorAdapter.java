package cheetah.fighter.governor.support;

import cheetah.fighter.core.plugin.PluginChain;
import cheetah.fighter.governor.AbstractGovernorAdapter;
import cheetah.fighter.worker.Worker;

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
