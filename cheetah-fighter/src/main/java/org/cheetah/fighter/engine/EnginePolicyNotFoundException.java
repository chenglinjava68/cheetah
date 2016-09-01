package org.cheetah.fighter.engine;


import org.cheetah.fighter.api.FighterException;

/**
 * Created by Max on 2016/3/2.
 */
public class EnginePolicyNotFoundException extends FighterException {

    public EnginePolicyNotFoundException(Throwable cause) {
        super(" No corresponding strategy", cause);
    }
}
