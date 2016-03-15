package cheetah.fighter.core;

import cheetah.fighter.commons.CheetahException;

/**
 * Created by Max on 2016/2/17.
 */
public class ErrorEventTypeException extends CheetahException {
    public ErrorEventTypeException() {
        super("The incident no listener corresponding processing.");
    }
}
