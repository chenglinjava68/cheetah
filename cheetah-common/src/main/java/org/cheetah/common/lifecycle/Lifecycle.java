package org.cheetah.common.lifecycle;

/**
 * <p>Lifecycle class.</p>
 *
 * @author Max
 * @version $Id: $Id
 */
public final class Lifecycle {

    public enum State {
        INITIALIZED,
        STOPPED,
        STARTED,
        CLOSED
    }

    private volatile State state = State.INITIALIZED;

    /**
     * <p>state.</p>
     *
     * @return a {@link Lifecycle.State} object.
     */
    public State state() {
        return this.state;
    }

    /**
     * <p>initialized.</p>
     *
     * @return a boolean.
     */
    public boolean initialized() {
        return state == State.INITIALIZED;
    }

    /**
     * <p>started.</p>
     *
     * @return a boolean.
     */
    public boolean started() {
        return state == State.STARTED;
    }

    /**
     * <p>stopped.</p>
     *
     * @return a boolean.
     */
    public boolean stopped() {
        return state == State.STOPPED;
    }

    /**
     * <p>closed.</p>
     *
     * @return a boolean.
     */
    public boolean closed() {
        return state == State.CLOSED;
    }

    /**
     * <p>stoppedOrClosed.</p>
     *
     * @return a boolean.
     */
    public boolean stoppedOrClosed() {
        Lifecycle.State localState = this.state;
        return localState == State.STOPPED || localState == State.CLOSED;
    }

    /**
     * <p>canMoveToStarted.</p>
     *
     * @return a boolean.
     */
    public boolean canMoveToStarted() {
        State localState = this.state;
        return localState == State.INITIALIZED || localState == State.STOPPED;
    }

    /**
     * <p>moveToStarted.</p>
     *
     * @return a boolean.
     */
    public boolean moveToStarted() {
        State localState = this.state;
        if (localState == State.INITIALIZED || localState == State.STOPPED) {
            state = State.STARTED;
            return true;
        }
        if (localState == State.STARTED) {
            return false;
        }

        throw new LifecycleException(String.format("state[%s] can't move to STARTED.", localState));
    }

    /**
     * <p>canMoveToStopped.</p>
     *
     * @return a boolean.
     */
    public boolean canMoveToStopped() {
        State localState = state;
        return localState == State.STARTED;
    }

    /**
     * <p>moveToStopped.</p>
     *
     * @return a boolean.
     */
    public boolean moveToStopped() {
        State localState = state;
        if (localState == State.STARTED) {
            state = State.STOPPED;
            return true;
        }
        if (localState == State.INITIALIZED || localState == State.STOPPED) {
            return false;
        }
        throw new LifecycleException(String.format("state[%s] can't move to STOPPED.", localState));
    }

    /**
     * <p>canMoveToClosed.</p>
     *
     * @return a boolean.
     */
    public boolean canMoveToClosed() {
        State localState = state;
        return localState != State.CLOSED;
    }

    /**
     * <p>moveToClosed.</p>
     *
     * @return a boolean.
     */
    public boolean moveToClosed() {
        State localState = state;
        if (localState == State.CLOSED) {
            return false;
        }

        state = State.CLOSED;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return state.toString();
    }
}
