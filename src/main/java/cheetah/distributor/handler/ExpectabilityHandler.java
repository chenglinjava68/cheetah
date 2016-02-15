package cheetah.distributor.handler;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Max on 2016/2/14.
 */
public interface ExpectabilityHandler extends Handler {
    CompletableFuture<Boolean> getFuture();
}
