package cheetah.dispatcher.machine;

import cheetah.dispatcher.governor.Command;

/**
 * Created by Max on 2016/2/16.
 */
public class CallbackWrapper {
    private boolean exception;
    private Command command;
    private Class<?> exceptionObject;
    private String exceptionMessage;

    public CallbackWrapper(boolean exception, Command command, Class<?> exceptionObject, String exceptionMessage) {
        this.exception = exception;
        this.command = command;
        this.exceptionObject = exceptionObject;
        this.exceptionMessage = exceptionMessage;
    }

    public boolean isException() {
        return exception;
    }

    public Command getCommand() {
        return command;
    }

    public Class<?> getExceptionObject() {
        return exceptionObject;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
