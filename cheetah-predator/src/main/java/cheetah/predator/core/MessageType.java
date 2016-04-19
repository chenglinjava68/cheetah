package cheetah.predator.core;

/**
 * Created by Max on 2016/3/26.
 */
public enum MessageType {
    CMD(0), //����
    ACK(1), //ȷ��ack
    CHAT(2), //����
    SYSTEM(3), //ϵͳ
    NOTICE(4),  //֪ͨ
    EVENT(5),  //�¼�
    APP(6),   //Ӧ��
    VOIP(7),  //����
    CUSTOM(8); //�Զ���

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
