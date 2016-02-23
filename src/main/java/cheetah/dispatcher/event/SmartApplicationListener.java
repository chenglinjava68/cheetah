package cheetah.dispatcher.event;

/**
 * Created by Max on 2016/1/29.
*/
public interface SmartApplicationListener<E extends ApplicationEvent> extends ApplicationListener<E> {
    boolean supportsEventType(Class<? extends ApplicationEvent> eventType);

    boolean supportsSourceType(Class<?> sourceType);

    int getOrder();
}
