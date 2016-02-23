package cheetah.distributor.governor;

import cheetah.distributor.event.Event;
import cheetah.distributor.machine.Machine;

import java.util.List;

/**
 * Created by Max on 2016/2/22.
 */
public class CommandFactory {
    Event event;
    boolean fisrtWin;
    boolean needReport;
    List<Machine> machines;

    public Command build() {
        return new Command(this);
    }

    public static CommandFactory getFactory() {
        return new CommandFactory();
    }

    public CommandFactory setEvent(Event event) {
        this.event = event;
        return this;
    }

    public CommandFactory setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
        return this;
    }

    public CommandFactory setNeedReport(boolean needReport) {
        this.needReport = needReport;
        return this;
    }

    public CommandFactory setMachines(List<Machine> machines) {
        this.machines = machines;
        return this;
    }
}
