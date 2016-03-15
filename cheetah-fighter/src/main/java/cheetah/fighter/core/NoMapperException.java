package cheetah.fighter.core;

import cheetah.commons.CheetahException;

/**
 * Created by Max on 2016/2/23.
 */
public class NoMapperException extends CheetahException {
    public NoMapperException() {
        super("Couldn't find the corresponding mapping.");
    }
}
