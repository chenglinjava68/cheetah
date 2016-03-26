package cheetah.predator.core;

/**
 * Created by Max on 2016/3/13.
 */
public interface Request {

    void setMessage(Message message);

    Message getMessage();

    Session getSession();
}
