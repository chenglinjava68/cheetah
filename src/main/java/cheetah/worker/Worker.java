package cheetah.worker;

/**
 * Created by Max on 2016/2/19.
 */
public interface Worker extends Cloneable {
    void work(Command order);
}
