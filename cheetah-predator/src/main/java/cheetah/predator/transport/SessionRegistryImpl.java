package cheetah.predator.transport;

import cheetah.commons.logger.Loggers;
import cheetah.predator.core.Session;
import cheetah.predator.core.SessionRegistry;
import com.google.common.collect.Maps;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * @author siuming
 */
public class SessionRegistryImpl implements SessionRegistry {

    private final ConcurrentMap<String, Session> sessions = Maps.newConcurrentMap();

    @Override
    public Optional<Session> lookup(String remoteEndpoint) {
        return lookup(URI.create(remoteEndpoint));
    }

    @Override
    public Optional<Session> lookup(URI remoteEndpoint) {
        Session session = this.sessions.get(calcSessionKey(remoteEndpoint));
        return Optional.ofNullable(session);
    }

    private String calcSessionKey(URI remoteEndpoint) {
        return String.format("%s-%s-%s", remoteEndpoint.getScheme(), remoteEndpoint.getHost(), remoteEndpoint.getPort());
    }

    @Override
    public void register(Session session) {
        URI uri = URI.create(session.metadata().remoteEndpoint());
        Loggers.me().debug(getClass(), "session[{}] registered.", uri);
        this.sessions.put(calcSessionKey(uri), session);
    }

    @Override
    public void unregister(Session session) {
        URI uri = URI.create(session.metadata().remoteEndpoint());
        Loggers.me().debug(getClass(), "session[{}] unregistered.", uri);
        this.sessions.remove(calcSessionKey(uri));
    }
}
