package cheetah.fighter.protocol;

import java.util.Map;

/**
 * Created by Max on 2016/3/12.
 */
public final class Message {
    private Header header;
    private Map<String, Object> body;

    public final Header header() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Map<String, Object> body() {
        return body;
    }

    public final void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
