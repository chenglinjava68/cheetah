package cheetah.distributor.machine.support;

import cheetah.distributor.event.ApplicationEvent;
import cheetah.distributor.event.ApplicationListener;
import cheetah.distributor.event.Event;
import cheetah.distributor.machine.AbstractMachine;
import cheetah.distributor.machine.Report;

/**
 * Created by Max on 2016/2/1.
 */
public class ApplicationEventMachine extends AbstractMachine {

    @Override
    public void work(Event event) {
        try {
            ApplicationListener listener = ((ApplicationListener) this.getEventListener());
            listener.onApplicationEvent((ApplicationEvent) event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report completeWork(Event event) {
        try {
            ApplicationListener listener = ((ApplicationListener) this.getEventListener());
            listener.onApplicationEvent((ApplicationEvent) event);
        } catch (Exception e) {
            e.printStackTrace();
            return Report.FAILURE;
        }
        return Report.SUCCESS;
    }


}
