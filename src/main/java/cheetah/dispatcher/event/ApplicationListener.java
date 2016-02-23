package cheetah.dispatcher.event;

import java.util.EventListener;

/**
 * Created by Max on 2016/1/29.
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
