package cheetah.distributor.worker;

/**
 * Created by Max on 2016/2/16.
 */
public class CallbackWrapper {
    private boolean exception;
    private Order eventMessage;
    private Class<?> exceptionObject;
    private String exceptionMessage;

    public CallbackWrapper(boolean exception, Order eventMessage, Class<?> exceptionObject, String exceptionMessage) {
        this.exception = exception;
        this.eventMessage = eventMessage;
        this.exceptionObject = exceptionObject;
        this.exceptionMessage = exceptionMessage;
    }

    public boolean isException() {
        return exception;
    }

    public Order getEventMessage() {
        return eventMessage;
    }

    public Class<?> getExceptionObject() {
        return exceptionObject;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
