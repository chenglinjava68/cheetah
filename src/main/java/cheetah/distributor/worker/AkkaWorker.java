package cheetah.distributor.worker;

import akka.actor.ActorRef;
import cheetah.util.Assert;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaWorker implements Worker {
    private ActorRef workUnit;

    @Override
    public void execute(Instruction instruction) {
        Assert.notNull(workUnit, "workUnit must not be null");
        this.workUnit.tell(instruction, workUnit);
    }

    @Override
    public Worker kagebunsin() throws CloneNotSupportedException {
        return (Worker) super.clone();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        while (this.workUnit.isTerminated())
            this.workUnit.tell(Instruction.CLOSE, workUnit);
    }

    public void setWorkUnit(ActorRef workUnit) {
        this.workUnit = workUnit;
    }
}
