package org.cheetah.fighter.governor;

import akka.actor.ActorRef;
import org.cheetah.fighter.core.governor.AbstractGovernorAdapter;
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
