package cheetah.distributor.governor;

import cheetah.distributor.event.Event;
import cheetah.distributor.machine.Machine;
import cheetah.util.ObjectUtils;

import java.util.List;

/**
 * Created by Max on 2016/2/7.
 */
public class Command {
    public static final Command CLOSE = new Command();
    private Event event;
    private boolean fisrtWin;
    private boolean needReport;
    private List<Machine> machines;

    Command() {
    }

    public Command(CommandFactory commandFactory) {
        this.event = commandFactory.event;
        this.fisrtWin = commandFactory.fisrtWin;
        this.needReport = commandFactory.needReport;
        this.machines = commandFactory.machines;
    }

    public static boolean isClose(Command command) {
        return command.equals(CLOSE);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isFisrtWin() {
        return fisrtWin;
    }

    public void setFisrtWin(boolean fisrtWin) {
        this.fisrtWin = fisrtWin;
    }

    public void setNeedReport(boolean needReport) {
        this.needReport = needReport;
    }

    public boolean isNeedReport() {
        return needReport;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setWorkers(List<Machine> machines) {
        this.machines = machines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command that = (Command) o;

        return ObjectUtils.nullSafeEquals(this.event, that.event) &&
                ObjectUtils.nullSafeEquals(this.fisrtWin, that.fisrtWin) &&
                ObjectUtils.nullSafeEquals(this.machines, that.machines) &&
                ObjectUtils.nullSafeEquals(this.needReport, that.needReport);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.event) * 29 +
                ObjectUtils.nullSafeHashCode(this.fisrtWin) + ObjectUtils.nullSafeHashCode(this.needReport)
                + ObjectUtils.nullSafeHashCode(this.machines);
    }
}
