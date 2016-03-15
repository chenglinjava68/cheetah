package cheetah.predator.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 2016/3/12.
 */
public final class Header {

    private int crcCode = 0xabef0101;
    private int length;
    private long sessionId;
    private int type;
    private int priority;
    private String deliveryId;
    private long deliveryTime;
    private Map<String, Object> attachment = new HashMap<>();

    public final int crcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int length() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long sessionId() {
        return sessionId;
    }

    public final void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public final Type type() {
        return Type.formatFrom(this.type);
    }

    public final int rawType() {
        return this.type;
    }

    public final void setType(int type) {
        this.type = type;
    }

    public final int priority() {
        return priority;
    }

    public final void setPriority(int priority) {
        this.priority = priority;
    }

    public final String deliveryId() {
        return deliveryId;
    }

    public final void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public final long deliveryTime() {
        return deliveryTime;
    }

    public final void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public final Map<String, Object> attachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public enum Type {
        CMD(0),
        ACK(1),
        CHAT(2),
        SYSTEM(3),
        NOTICE(4),
        EVENT(5),
        APP(6),
        VOIP(7),
        CUSTOM(8);

        Type(int type) {
            this.type = type;
        }

        private int type;

        static Type formatFrom(int type) {
            for (Type t : Type.values()) {
                if (t.type == type) {
                    return t;
                }
            }
            throw new ProtocolTypeNotFoundException();
        }
    }
}
