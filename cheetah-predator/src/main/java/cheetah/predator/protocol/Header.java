package cheetah.predator.protocol;

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
    private Map<String, Object> attachment;

    public Header(Builder builder) {
        this.crcCode = builder.crcCode;
        this.length = builder.length;
        this.sessionId = builder.sessionId;
        this.type = builder.type;
        this.priority = builder.priority;
        this.deliveryId = builder.deliveryId;
        this.deliveryTime = builder.deliveryTime;
        this.attachment = builder.attachment;
    }

    public final int crcCode() {
        return crcCode;
    }

    public final int length() {
        return length;
    }

    public final long sessionId() {
        return sessionId;
    }

    public final Type type() {
        return Type.formatFrom(this.type);
    }

    public final int rawType() {
        return this.type;
    }

    public final int priority() {
        return priority;
    }

    public final String deliveryId() {
        return deliveryId;
    }

    public final long deliveryTime() {
        return deliveryTime;
    }

    public final Map<String, Object> attachment() {
        return attachment;
    }

    public final static class Builder {
        int crcCode = 0xabef0101;
        int length;
        long sessionId;
        int type;
        int priority;
        String deliveryId;
        long deliveryTime;
        Map<String, Object> attachment;

        public Header build() {
            return new Header(this);
        }

        public Builder setSessionId(long sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder setCrcCode(int crcCode) {
            this.crcCode = crcCode;
            return this;
        }

        public Builder setLength(int length) {
            this.length = length;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
            return this;
        }

        public Builder setDeliveryTime(long deliveryTime) {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public Builder setAttachment(Map<String, Object> attachment) {
            this.attachment = attachment;
            return this;
        }
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
