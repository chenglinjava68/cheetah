package cheetah.distributor.handler;

/**
 * Created by Max on 2016/2/16.
 */
public class CallbackWrapper {
    private boolean exception;
    private EventMessage eventMessage;
    private Class<?> exceptionObject;
    private String exceptionMessage;

    public CallbackWrapper(boolean exception, EventMessage eventMessage, Class<?> exceptionObject, String exceptionMessage) {
        this.exception = exception;
        this.eventMessage = eventMessage;
        this.exceptionObject = exceptionObject;
        this.exceptionMessage = exceptionMessage;
    }

    public boolean isException() {
        return exception;
    }

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public Class<?> getExceptionObject() {
        return exceptionObject;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
