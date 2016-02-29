package cheetah.governor;

import cheetah.machine.Machine;

/**
 * Created by Max on 2016/2/24.
 */
public class TaskResult<T> {
    private T data;
    private boolean success;
    private Machine machine;

    private TaskResult(){}

    private TaskResult(T data, boolean success, Machine machine) {
        this.data = data;
        this.success = success;
        this.machine = machine;
    }

    public static <T> TaskResult<T> invoke(T data, Machine machine) {
        return new TaskResult<>(data, false, machine);
    }

    public static <T> TaskResult<T> success(T data) {
        return new TaskResult<>(data, true, null);
    }

    public static <T> TaskResult<T> failure(T data, Machine machine) {
        return new TaskResult<>(data, false, machine);
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "data=" + data +
                ", success=" + success +
                ", machine='" + machine + '\'' +
                '}';
    }
}
