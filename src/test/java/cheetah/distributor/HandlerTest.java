package cheetah.distributor;

import cheetah.logger.Debug;
import org.junit.Test;

/**
 * Created by Max on 2016/2/2.
 */
public class HandlerTest {
    @Test
    public void log() {
        Debug.log(Distributor.class, "a");
    }
}
