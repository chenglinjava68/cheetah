package cheetah.distributor.worker;

import cheetah.distributor.Startable;

/**
 * Created by Max on 2016/2/19.
 */
public interface Worker extends Startable, Cloneable {
    default void execute(Instruction instruction) {
        start();
    }

    Object kagebunsin() throws CloneNotSupportedException;

    default boolean supporsType(Class<?> task) {
        if (task.equals(Instruction.class))
            return true;
        return false;
    }
}
