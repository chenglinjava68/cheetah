package cheetah.distributor;

/**
 * Created by Max on 2016/2/17.
 */
public class DistributorStateException extends RuntimeException {
    public DistributorStateException() {
        super("Distributor Not Started");
    }
}
