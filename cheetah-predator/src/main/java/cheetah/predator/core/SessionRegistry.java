package cheetah.predator.core;

import java.net.URI;
import java.util.Optional;

/**
 * @author siuming
 */
public interface SessionRegistry {

    /**
     * @param remoteEndpoint
     * @return
     */
    Optional<Session> lookup(String remoteEndpoint);

    /**
     * @param remoteEndpoint
     * @return
     */
    Optional<Session> lookup(URI remoteEndpoint);

    /**
     * @param session
     */
    void register(Session session);

    /**
     * @param session
     */
    void unregister(Session session);

    int size();
}
