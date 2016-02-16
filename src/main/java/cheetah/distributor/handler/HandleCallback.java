package cheetah.distributor.handler;

import cheetah.distributor.EventMessage;

/**
 * Created by Max on 2016/2/16.
 */
public interface HandleCallback {
    void doInHandler(boolean exception, EventMessage eventMessage, Class<?> exceptionObject, String exceptionMessage);
}
