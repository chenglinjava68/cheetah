package cheetah.distributor.handler;

/**
 * Created by Max on 2016/2/16.
 */
public interface HandleCallback {
    void doInHandler(boolean exception, EventMessage eventMessage, Class<?> exceptionObject, String exceptionMessage);
}
