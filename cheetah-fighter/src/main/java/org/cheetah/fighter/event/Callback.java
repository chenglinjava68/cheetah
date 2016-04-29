package org.cheetah.fighter.event;

/**
 * Created by Max on 2016/4/29.
 */
@FunctionalInterface
public interface Callback {

    void call(boolean passed, Object data);
}
