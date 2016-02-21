package cheetah.distributor.worker;

/**
 * Created by Max on 2016/2/16.
 */
public interface HandleCallback {
    void doInHandler(boolean exception, Order eventMessage, Class<?> exceptionObject, String exceptionMessage);
}
