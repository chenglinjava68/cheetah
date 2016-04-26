package org.cheetah.predator.core;


import org.cheetah.commons.net.MetaData;
import org.cheetah.commons.utils.Assert;
import org.cheetah.commons.utils.IDGenerator;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Max on 2016/4/21.
 */
public class Message implements MetaData {
    private static final long serialVersionUID = 5771462506829264968L;
    private MessageType type;
    private String from;
    private String to;
    private Map<String, Object> body;
    private String deliveryId;
    private long deliveryTime;

    public MessageType getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }


    Message() {
    }

    Message(Message.Builder builder) {
        this.type = builder.type;
        this.from = builder.from;
        this.to = builder.to;
        this.body = builder.body;
        this.deliveryId = builder.deliveryId;
        this.deliveryTime = builder.deliveryTime;
    }

    public MessageType type() {
        return this.type;
    }

    public String from() {
        return this.from;
    }

    public String to() {
        return this.to;
    }

    public Map<String, Object> body() {
        return this.body;
    }

    public <T> T body(String name) {
        return (T) this.body().get(name);
    }

    public String deliveryId() {
        return this.deliveryId;
    }

    public long deliveryTime() {
        return this.deliveryTime;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.deliveryId});
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(null != obj && this.getClass() == obj.getClass()) {
            Message that = (Message)obj;
            return Objects.equals(this.deliveryId, that.deliveryId);
        } else {
            return false;
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Message.Builder toBuilder() {
        return ofType(this.type).from(this.from).to(this.to).body(this.body).deliveryId(this.deliveryId).deliveryTime(Long.valueOf(this.deliveryTime));
    }

    public static Message.Builder ofType(String type) {
        return ofType(MessageType.valueOf(type));
    }

    public static Message.Builder ofType(MessageType type) {
        Assert.notNull(type, "type must not be null.");
        return new Message.Builder(type);
    }

    public static Message.Builder chatType() {
        return ofType(MessageType.CHAT);
    }

    public static Message.Builder cmdType() {
        return ofType(MessageType.CMD);
    }

    public static Message.Builder ackType() {
        return ofType(MessageType.ACK);
    }

    public static Message.Builder eventType() {
        return ofType(MessageType.EVENT);
    }

    public static Message.Builder customType() {
        return ofType(MessageType.CUSTOM);
    }

    public static Message.Builder systemType() {
        return ofType(MessageType.SYSTEM);
    }

    public static Message.Builder noticeType() {
        return ofType(MessageType.NOTICE);
    }

    public static Message.Builder appType() {
        return ofType(MessageType.APP);
    }

    public static Message.Builder voipType() {
        return ofType(MessageType.VOIP);
    }

    public static class Builder {
        MessageType type;
        String from;
        String to;
        Map<String, Object> body = Maps.newHashMap();
        String deliveryId = IDGenerator.generateId();
        long deliveryTime = System.currentTimeMillis();

        Builder(MessageType type) {
            Assert.notNull(type, "type must not be null.");
            this.type = type;
        }

        public Message.Builder from(String from) {
            this.from = from;
            return this;
        }

        public Message.Builder to(String to) {
            this.to = to;
            return this;
        }

        public Message.Builder body(String name, Object value) {
            this.body.put(name, value);
            return this;
        }

        public Message.Builder body(Map<String, Object> body) {
            this.body = Maps.newHashMap(body);
            return this;
        }

        public Message.Builder deliveryId(String deliveryId) {
            Assert.hasText(deliveryId, "deliveryId must not be null or empty.");
            this.deliveryId = deliveryId;
            return this;
        }

        public Message.Builder deliveryTime(Long deliveryTime) {
            Assert.notNull(deliveryTime, "deliveryTime must not be null.");
            this.deliveryTime = deliveryTime.longValue();
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
