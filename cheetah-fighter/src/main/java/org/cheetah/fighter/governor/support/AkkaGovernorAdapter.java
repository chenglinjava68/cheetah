package org.cheetah.fighter.governor.support;

import akka.actor.ActorRef;
import org.cheetah.fighter.governor.AbstractGovernorAdapter;
import org.cheetah.fighter.plugin.PluginChain;

/**
 * Created by Max on 2016/3/7.
 */
public class AkkaGovernorAdapter extends AbstractGovernorAdapter {
    public AkkaGovernorAdapter(AkkaGovernor governor, PluginChain pluginChain) {
        super(governor, pluginChain);
    }

    public void setWorker(ActorRef worker) {
        ((AkkaGovernor) adaptee()).setWorker(worker);
    }
}
