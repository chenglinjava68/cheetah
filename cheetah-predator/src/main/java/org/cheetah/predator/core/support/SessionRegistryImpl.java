package org.cheetah.predator.core.support;

import cheetah.commons.logger.Loggers;
import org.cheetah.predator.core.Session;
import org.cheetah.predator.core.SessionRegistry;
import com.google.common.collect.Maps;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Max
 */
public class SessionRegistryImpl implements SessionRegistry {

    private final ConcurrentMap<String, Session> sessions = Maps.newConcurrentMap();
    private final ConcurrentMap<String, Session> ratifySessions = Maps.newConcurrentMap();

    @Override
    public Optional<Session> lookup(String remoteEndpoint) {
        return lookup(URI.create(remoteEndpoint));
    }

    @Override
    public Optional<Session> lookup(URI remoteEndpoint) {
        Session session = this.ratifySessions.get(calcSessionKey(remoteEndpoint));
        if(Objects.isNull(session))
            session= this.sessions.get(calcSessionKey(remoteEndpoint));
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
        this.ratifySessions.remove(calcSessionKey(uri));
    }

    @Override
    public void ratify(Session session) {
        Session.Metadata metadata = session.metadata().toBuilder().state(Session.State.APPROVED).build();
        session.metadata(metadata);
        URI uri = URI.create(session.metadata().remoteEndpoint());
        ratifySessions.put(calcSessionKey(uri), session);
        sessions.remove(calcSessionKey(uri));
    }

    @Override
    public int size() {
        return sessions.size();
    }

    @Override
    public int ratifySize() {
        return ratifySessions.size();
    }
}
