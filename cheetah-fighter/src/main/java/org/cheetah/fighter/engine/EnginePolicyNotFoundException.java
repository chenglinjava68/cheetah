package org.cheetah.fighter.engine;


import org.cheetah.fighter.core.FighterException;

/**
 * Created by Max on 2016/3/2.
 */
public class EnginePolicyNotFoundException extends FighterException {
    public EnginePolicyNotFoundException() {
        super("There is no policy.");
    }
}
