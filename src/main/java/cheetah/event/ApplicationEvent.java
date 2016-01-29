package cheetah.event;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class ApplicationEvent implements Event<Object> {
    private Long occurredTime = System.currentTimeMillis();
    private Object source;

    public ApplicationEvent(Object source) {
        this.source = source;
    }

    @Override
    public Long occurredTime() {
        return this.occurredTime;
    }

    @Override
    public Object getSource() {
        return this.source;
    }


}
