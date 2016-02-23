package cheetah.distributor.core;

/**
 * Created by Max on 2016/2/23.
 */
public interface Dispatcher {
    EventResult receive(EventMessage eventMessage);

    EventResult dispatch(EventMessage eventMessage);
}
