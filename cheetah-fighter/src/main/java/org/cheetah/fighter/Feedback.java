package org.cheetah.fighter;


/**
 * 机器的反馈
 * Created by Max on 2016/2/21.
 */
public class Feedback<T> {
    public static final Feedback<String> EMPTY = new Feedback<>("empty", false);
    public static final Feedback<String> FAILURE = new Feedback<>(null, true); //
    public static final Feedback<String> SUCCESS = new Feedback<>(null, false); //
    private T data;
    private boolean success;

    private Feedback() {
    }

    private Feedback(T data, boolean fail) {
        this.data = data;
        this.success = fail;
    }

    public static <T> Feedback<T> success(T data) {
        return new Feedback<>(data, Boolean.TRUE);
    }

    public static <T> Feedback<T> failure(T data) {
        return new Feedback<>(data, Boolean.FALSE);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback<?> feedback = (Feedback<?>) o;

        if (success != feedback.success) return false;
        return !(data != null ? !data.equals(feedback.data) : feedback.data != null);

    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (success ? 1 : 0);
        return result;
    }
}
