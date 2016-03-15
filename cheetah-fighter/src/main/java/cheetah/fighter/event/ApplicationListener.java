package cheetah.fighter.event;

import java.io.Serializable;
import java.util.EventListener;

/**
 * Created by Max on 2016/1/29.
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener, Serializable {
    void onApplicationEvent(E event);
}
