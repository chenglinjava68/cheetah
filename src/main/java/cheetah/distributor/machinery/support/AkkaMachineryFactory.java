package cheetah.distributor.machinery.support;

import cheetah.distributor.machinery.AkkaMachinery;
import cheetah.distributor.machinery.Machinery;
import cheetah.distributor.machinery.MachineryFactory;

/**
 * Created by Max on 2016/2/21.
 */
public class AkkaMachineryFactory implements MachineryFactory {

    @Override
    public Machinery createMachinery() {
        return new AkkaMachinery();
    }

}
