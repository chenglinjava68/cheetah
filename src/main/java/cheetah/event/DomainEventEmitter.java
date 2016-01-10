package cheetah.event;

/**
 * Created by Max on 2016/1/10.
 */
public interface DomainEventEmitter {
    void addChangeEvent();
    void removeChangeEvent();
}
