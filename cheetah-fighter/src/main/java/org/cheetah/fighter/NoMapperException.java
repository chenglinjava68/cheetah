package org.cheetah.fighter;

/**
 * 触发事件如若没有找到相应的消费者进行消费，将触发该异常
 * Created by Max on 2016/2/23.
 */
public class NoMapperException extends FighterException {
    public NoMapperException() {
        super("Couldn't find the corresponding mapping.");
    }
}
