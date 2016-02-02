package cheetah.distributor;

/**
 * Created by Max on 2016/1/29.
 */
public class EventResult<R> {
    private R body;

    public EventResult(R body) {
        this.body = body;
    }

    public R getBody() {
        return body;
    }
}
