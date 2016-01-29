package cheetah.event;

/**
 * Created by Max on 2016/1/29.
 */
public interface Event<E> {
    Long occurredTime();

    E getSource();
}
