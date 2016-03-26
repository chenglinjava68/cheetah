package cheetah.predator.core;

/**
 * Created by Max on 2016/3/26.
 */
public enum MessageType {
    CMD(0),
    ACK(1),
    CHAT(2),
    SYSTEM(3),
    NOTICE(4),
    EVENT(5),
    APP(6),
    VOIP(7),
    CUSTOM(8);

    MessageType(int type) {
        this.type = type;
    }

    private int type;

    static MessageType formatFrom(int type) {
        for (MessageType t : MessageType.values()) {
            if (t.type == type) {
                return t;
            }
        }
        throw new MessageTypeNotFoundException();
    }

    public int type() {
        return type;
    }
}
