package cheetah.core;

import cheetah.exceptions.CheetahException;

/**
 * Created by Max on 2016/2/17.
 */
public class DistributorStateException extends CheetahException {
    public DistributorStateException() {
        super("Distributor Not Started");
    }
}
