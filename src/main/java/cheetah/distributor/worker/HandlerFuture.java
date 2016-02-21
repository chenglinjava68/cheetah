package cheetah.distributor.worker;

/**
 * Created by Max on 2016/2/16.
 */
public interface HandlerFuture {
    void call(Order eventMessage);

    void setTimeout(int timeout);

    Object get();
}
