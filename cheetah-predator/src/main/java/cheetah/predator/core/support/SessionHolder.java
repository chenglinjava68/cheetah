package cheetah.predator.core.support;

import cheetah.predator.core.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * Created by Max on 2016/3/13.
 */
public class SessionHolder {

    static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf(Session.class.getName());

    private SessionHolder() {
    }
    /**
     * @param ctx
     * @return
     */
    public static Session getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(SESSION_KEY).get();
    }

    /**
     * @param ctx
     * @return
     */
    public static Session getAndRemoveSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(SESSION_KEY).getAndRemove();
    }

    /**
     * @param ctx
     * @param session
     */
    public static void setSession(ChannelHandlerContext ctx, Session session) {
        ctx.channel().attr(SESSION_KEY).set(session);
    }

}
