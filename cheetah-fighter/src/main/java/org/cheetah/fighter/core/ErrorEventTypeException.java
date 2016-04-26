package org.cheetah.fighter.core;

import org.cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/2/17.
 */
public class ErrorEventTypeException extends PlatformException {
    public ErrorEventTypeException() {
        super("The incident no listener corresponding processing.");
    }
}
