package cheetah.distributor.machinery;

import cheetah.distributor.Startable;

/**
 * Created by Max on 2016/2/19.
 */
public interface Machinery extends Startable, Cloneable {
    void execute(Instruction instruction);

    Machinery copy();

    default boolean supporsType(Class<?> task) {
        if(task.equals(Instruction.class))
            return true;
        return false;
    }
}
