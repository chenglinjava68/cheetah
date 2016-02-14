package cheetah.distributor;

import cheetah.distributor.handler.HandlerTyped;
import cheetah.event.DomainEventListener;
import cheetah.logger.Debug;
import org.junit.Test;

/**
 * Created by Max on 2016/2/2.
 */
public class HandlerTest {
    @Test
    public void log() {
        Debug.log(Distributor.class, "a");
    }

    @Test
    public void handlers() {
        HandlerTyped typed = HandlerTyped.Manager.convertFrom(DomainEventListener.class);
        System.out.println(typed);
    }
}
