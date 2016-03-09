package cheetah.governor.support;

import cheetah.core.plugin.PluginChain;
import cheetah.governor.AbstractGovernorAdapter;
import cheetah.worker.Worker;

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
