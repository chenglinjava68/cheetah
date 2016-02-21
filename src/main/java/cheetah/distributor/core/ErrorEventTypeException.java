package cheetah.distributor.core;

import cheetah.exceptions.CheetahException;

/**
 * Created by Max on 2016/2/17.
 */
public class ErrorEventTypeException extends CheetahException {
    public ErrorEventTypeException() {
        super("The incident no listener corresponding processing.");
    }
}
