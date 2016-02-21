package cheetah.distributor.worker;

import cheetah.exceptions.CheetahException;

/**
 * Created by Max on 2016/2/1.
 */
public class HandlerTypedNotFoundException extends CheetahException {

    public HandlerTypedNotFoundException() {
        super("handler tpye not found.");
    }
}
