package org.cheetah.rs.core;

/**
 * @author Max
 */
public final class ClientContext {

    static final ThreadLocal<Client> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * @return
     */
    public static Client get() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * @param client
     */
    public static void set(Client client) {
        CONTEXT_HOLDER.set(client);
    }

    /**
     *
     */
    public static void unset() {
        CONTEXT_HOLDER.remove();
    }

    private ClientContext() {
    }
}
