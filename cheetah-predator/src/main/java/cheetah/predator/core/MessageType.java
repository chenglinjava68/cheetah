package cheetah.predator.core;

/**
 * Created by Max on 2016/3/26.
 */
public enum MessageType {
    CMD(0), //命令
    ACK(1), //确认ack
    CHAT(2), //聊天
    SYSTEM(3), //系统
    NOTICE(4),  //通知
    EVENT(5),  //事件
    APP(6),   //应用
    VOIP(7),  //语音
    CUSTOM(8); //自定义

    MessageType(int value) {
        this.value = value;
    }

    private int value;

    public static MessageType formatFrom(int value) {
        for (MessageType t : MessageType.values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new MessageTypeNotFoundException();
    }

    public int value() {
        return value;
    }
}
