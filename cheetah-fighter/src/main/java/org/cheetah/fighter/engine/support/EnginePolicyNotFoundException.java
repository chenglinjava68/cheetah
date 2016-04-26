package org.cheetah.fighter.engine.support;


import org.cheetah.commons.PlatformException;

/**
 * Created by Max on 2016/3/2.
 */
public class EnginePolicyNotFoundException extends PlatformException {
    public EnginePolicyNotFoundException() {
        super("There is no policy.");
    }
}
