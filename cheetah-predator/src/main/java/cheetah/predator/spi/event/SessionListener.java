package cheetah.predator.spi.event;

/**
 * @author Max
 */
public interface SessionListener {

    /**
     * @param event
     */
    void onEvent(SessionEvent event);
}
