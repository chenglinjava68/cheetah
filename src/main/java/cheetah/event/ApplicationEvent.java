package cheetah.event;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class ApplicationEvent<E> implements Event<E> {
    private Long occurredTime = System.currentTimeMillis();
    private E source;

    public ApplicationEvent(E source) {
        this.source = source;
    }

    @Override
    public Long occurredTime() {
        return this.occurredTime;
    }

    @Override
    public E getSource() {
        return this.source;
    }
}
