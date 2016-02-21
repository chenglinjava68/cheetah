package cheetah.distributor.machinery;

import cheetah.distributor.machinery.actor.ActorUnit;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaMachinery implements Machinery {
    private ActorUnit unit;

    @Override
    public void execute(Instruction instruction) {

    }

    @Override
    public Machinery copy() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void setUnit(ActorUnit unit) {
        this.unit = unit;
    }
}
