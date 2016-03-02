package cheetah.engine.support;

import cheetah.exceptions.CheetahException;

/**
 * Created by Max on 2016/3/2.
 */
public class EnginePolicyNotFoundException extends CheetahException {
    public EnginePolicyNotFoundException() {
        super("There is no policy.");
    }
}
