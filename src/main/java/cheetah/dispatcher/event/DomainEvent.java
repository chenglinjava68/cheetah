package cheetah.dispatcher.event;


/**
 * Created by Max on 2016/1/29.
 */
public abstract class DomainEvent extends Event {
    public DomainEvent(Object source) {
        super(source);
    }
}
