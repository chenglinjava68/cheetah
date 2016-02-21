package cheetah.distributor.worker;

import java.util.EventListener;

/**
 * Created by Max on 2016/2/21.
 */
public class Report {
    private boolean fail;
    private Class<? extends EventListener> exceptionListener;

    public boolean isFail() {
        return fail;
    }

    public Class<? extends EventListener> getExceptionListener() {
        return exceptionListener;
    }
}
