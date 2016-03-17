package cheetah.predator.spi.event;

import cheetah.commons.logger.Loggers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Max
 */
public final class SessionListenerStream implements SessionListener {

    private List<SessionListener> listeners;

    public SessionListenerStream() {
        this.listeners = Lists.newArrayList();
    }

    /**
     * @param listeners
     */
    public SessionListenerStream(List<SessionListener> listeners) {
        this.listeners = ImmutableList.copyOf(listeners);
    }

    public void setListeners(List<SessionListener> listeners) {
        this.listeners = ImmutableList.copyOf(listeners);
    }

    @Override
    public void onEvent(SessionEvent event) {
        listeners.forEach(trigger(event));
    }

    private Consumer<? super SessionListener> trigger(SessionEvent event) {
        return listener -> {
            try {
                listener.onEvent(event);
            } catch (Exception ignore) {
                Loggers.me().warn(getClass(), "fire session event occurs error.", ignore);
            }
        };
    }
}
