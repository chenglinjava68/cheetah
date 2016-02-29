package cheetah.event;

/**
 * Created by Max on 2016/1/29.
 */
public abstract class ApplicationEvent extends Event {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }


}
