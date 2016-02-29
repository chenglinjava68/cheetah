package cheetah.machine;


/**
 * Created by Max on 2016/2/21.
 */
public class Feedback<T> {
    public static Feedback EMPTY = new Feedback("empty", false);
    public static Feedback FAILURE = new Feedback(null, false); //
    public static Feedback SUCCESS = new Feedback(null, true); //
    private T data;
    private boolean fail;

    private Feedback() {
    }

    private Feedback(T data, boolean fail) {
        this.data = data;
        this.fail = fail;
    }

    public static <T> Feedback<T> success(T data) {
        return new Feedback<>(data, Boolean.TRUE);
    }

    public static <T> Feedback<T> failure(T data) {
        return new Feedback<>(data, Boolean.FALSE);
    }

    public boolean isFail() {
        return fail;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback<?> feedback = (Feedback<?>) o;

        if (fail != feedback.fail) return false;
        return !(data != null ? !data.equals(feedback.data) : feedback.data != null);

    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (fail ? 1 : 0);
        return result;
    }
}
